package com.inventory.inventory_management_system.productimage.service.impl;

import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.productimage.dto.request.UploadProductImageRequest;
import com.inventory.inventory_management_system.productimage.dto.response.ProductImageResponse;
import com.inventory.inventory_management_system.productimage.entity.ProductImage;
import com.inventory.inventory_management_system.productimage.mapper.ProductImageMapper;
import com.inventory.inventory_management_system.productimage.repository.ProductImageRepository;
import com.inventory.inventory_management_system.productimage.service.ProductImageService;
import com.inventory.inventory_management_system.upload.storage.LocalStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository repository;
    private final ProductImageMapper mapper;
    private final LocalStorageService storageService;

    @Override
    @Transactional
    public ProductImageResponse uploadImage(@NonNull UploadProductImageRequest request) {
        String fileName = java.util.UUID.randomUUID().toString();
        String folder = "products";
        com.inventory.inventory_management_system.upload.dto.response.UploadResponse uploadResponse = storageService.store(request.getFile(), fileName, folder);
        String imageUrl = uploadResponse.getFileUrl();

        if (Boolean.TRUE.equals(request.getIsPrimary())) {
            repository.resetPrimaryImages(request.getProductId());
        }

        ProductImage productImage = ProductImage.builder()
                .productId(request.getProductId())
                .imageUrl(imageUrl)
                .isPrimary(request.getIsPrimary() != null && request.getIsPrimary())
                .build();

        return mapper.toResponse(repository.save(productImage));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductImageResponse> getImagesByProductId(@NonNull Long productId) {
        return repository.findByProductId(productId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductImageResponse setPrimaryImage(@NonNull Long imageId) {
        ProductImage image = repository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));

        repository.resetPrimaryImages(image.getProductId());
        image.setIsPrimary(true);

        return mapper.toResponse(repository.save(image));
    }

    @Override
    @Transactional
    public void deleteImage(@NonNull Long imageId) {
        ProductImage image = repository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));

        String fileName = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
        String folder = "products";
        storageService.delete(fileName, folder);
        repository.delete(image);
    }
}
