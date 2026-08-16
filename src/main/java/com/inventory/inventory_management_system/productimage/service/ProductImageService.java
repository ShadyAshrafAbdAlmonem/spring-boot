package com.inventory.inventory_management_system.productimage.service;

import com.inventory.inventory_management_system.productimage.dto.request.UploadProductImageRequest;
import com.inventory.inventory_management_system.productimage.dto.response.ProductImageResponse;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ProductImageService {
    ProductImageResponse uploadImage(@NonNull UploadProductImageRequest request);
    List<ProductImageResponse> getImagesByProductId(@NonNull Long productId);
    ProductImageResponse setPrimaryImage(@NonNull Long imageId);
    void deleteImage(@NonNull Long imageId);
}
