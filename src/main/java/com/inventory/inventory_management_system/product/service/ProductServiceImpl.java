package com.inventory.inventory_management_system.product.service;

import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.product.dto.request.*;
import com.inventory.inventory_management_system.product.dto.response.ProductDetailsResponse;
import com.inventory.inventory_management_system.product.dto.response.*;
import com.inventory.inventory_management_system.product.entity.Product;
import com.inventory.inventory_management_system.product.mapper.ProductMapper;
import com.inventory.inventory_management_system.product.repository.ProductRepository;
import com.inventory.inventory_management_system.product.specification.ProductSpecification;
import com.inventory.inventory_management_system.product.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    @Override
    @Transactional
    public ProductDetailsResponse createProduct(@NonNull CreateProductRequest request) { // @NonNull added to interface
        productValidator.validateCreate(request);
        Product product = productMapper.toEntity(request);
        product.setQrCode("QR-" + request.getSku());
        Product saved = Objects.requireNonNull(productRepository.save(product), "Saved product must not be null");
        return productMapper.toDetailsResponse(saved);
    }

    @Override
    @Transactional
    public ProductDetailsResponse updateProduct(@NonNull Long id, @NonNull UpdateProductRequest request) { // @NonNull added to interface
        Product product = findEntityById(id);
        productMapper.updateEntityFromDto(request, product);
        return productMapper.toDetailsResponse(Objects.requireNonNull(productRepository.save(product), "Saved product must not be null"));
    }

    @Override
    @Transactional
    public ProductDetailsResponse updateStock(@NonNull Long id, @NonNull UpdateStockRequest request) { // @NonNull added to interface
        Product product = findEntityById(id);
        productValidator.validateStockAdjustment(product.getQuantity(), request.getQuantityDelta());
        product.setQuantity(product.getQuantity() + request.getQuantityDelta());
        return productMapper.toDetailsResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailsResponse getProductById(@NonNull Long id) {
        return productMapper.toDetailsResponse(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailsResponse getProductBySku(@NonNull String sku) { // @NonNull added to interface
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + sku));
        return productMapper.toDetailsResponse(Objects.requireNonNull(product));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAllProducts(@NonNull ProductFilterRequest filter, int page, int size) { // @NonNull added to interface
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> productPage = productRepository.findAll(ProductSpecification.filterProducts(filter), pageable);

        List<ProductResponse> content = productPage.getContent().stream()
                .map(productMapper::toResponse)
                .toList();

        return PageResponse.<ProductResponse>builder()
                .content(content)
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public void deleteProduct(@NonNull Long id) {
        Product product = findEntityById(id);
        productRepository.delete(product);
    }

    private @NonNull Product findEntityById(@NonNull Long id) {
        return Objects.requireNonNull(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));
    }
}
