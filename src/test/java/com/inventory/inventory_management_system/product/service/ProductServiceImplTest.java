package com.inventory.inventory_management_system.product.service;

import com.inventory.inventory_management_system.common.exception.BadRequestException;
import com.inventory.inventory_management_system.common.exception.DuplicateResourceException;
import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.product.dto.request.CreateProductRequest;
import com.inventory.inventory_management_system.product.dto.request.ProductFilterRequest;
import com.inventory.inventory_management_system.product.dto.request.UpdateProductRequest;
import com.inventory.inventory_management_system.product.dto.request.UpdateStockRequest;
import com.inventory.inventory_management_system.product.dto.response.ProductDetailsResponse;
import com.inventory.inventory_management_system.product.entity.Product;
import com.inventory.inventory_management_system.product.mapper.ProductMapper;
import com.inventory.inventory_management_system.product.repository.ProductRepository;
import com.inventory.inventory_management_system.product.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductValidator productValidator;

    private ProductServiceImpl productService;

    private CreateProductRequest createRequest;
    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, productMapper, productValidator);

        createRequest = new CreateProductRequest();
        createRequest.setName("iPhone 15");
        createRequest.setSku("IPHONE-15");
        createRequest.setPrice(new BigDecimal("999.99"));
        createRequest.setCostPrice(new BigDecimal("700.00"));
        createRequest.setQuantity(10);
        createRequest.setMinQuantity(5);

        product = Product.builder()
                .id(1L)
                .name("iPhone 15")
                .sku("IPHONE-15")
                .price(new BigDecimal("999.99"))
                .costPrice(new BigDecimal("700.00"))
                .quantity(10)
                .minQuantity(5)
                .active(true)
                .build();
    }

    @Test
    void createProduct_shouldSetQrCodeAndReturnDetails() {
        Product productToSave = Product.builder()
                .name("iPhone 15")
                .sku("IPHONE-15")
                .price(new BigDecimal("999.99"))
                .costPrice(new BigDecimal("700.00"))
                .quantity(10)
                .minQuantity(5)
                .build();

        ProductDetailsResponse expectedResponse = new ProductDetailsResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("iPhone 15");
        expectedResponse.setSku("IPHONE-15");

        when(productMapper.toEntity(createRequest)).thenReturn(productToSave);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDetailsResponse(product)).thenReturn(expectedResponse);

        ProductDetailsResponse result = productService.createProduct(createRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(productToSave.getQrCode()).isEqualTo("QR-IPHONE-15");
        verify(productValidator).validateCreate(createRequest);
        verify(productRepository).save(productToSave);
    }

    @Test
    void createProduct_shouldThrowDuplicateWhenValidatorFails() {
        doThrow(new DuplicateResourceException("Product SKU already exists: IPHONE-15"))
                .when(productValidator).validateCreate(createRequest);

        assertThatThrownBy(() -> productService.createProduct(createRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("already exists");

        verify(productRepository, never()).save(any());
    }

    @Test
    void getProductById_shouldReturnProductWhenFound() {
        ProductDetailsResponse expectedResponse = new ProductDetailsResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("iPhone 15");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDetailsResponse(product)).thenReturn(expectedResponse);

        ProductDetailsResponse result = productService.getProductById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("iPhone 15");
    }

    @Test
    void getProductById_shouldThrowNotFoundWhenMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getProductBySku_shouldReturnProductWhenFound() {
        ProductDetailsResponse expectedResponse = new ProductDetailsResponse();
        expectedResponse.setSku("IPHONE-15");

        when(productRepository.findBySku("IPHONE-15")).thenReturn(Optional.of(product));
        when(productMapper.toDetailsResponse(product)).thenReturn(expectedResponse);

        ProductDetailsResponse result = productService.getProductBySku("IPHONE-15");

        assertThat(result).isNotNull();
        assertThat(result.getSku()).isEqualTo("IPHONE-15");
    }

    @Test
    void getProductBySku_shouldThrowNotFoundWhenMissing() {
        when(productRepository.findBySku("UNKNOWN")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductBySku("UNKNOWN"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("UNKNOWN");
    }

    @Test
    void updateStock_shouldIncreaseQuantity() {
        UpdateStockRequest request = new UpdateStockRequest();
        request.setQuantityDelta(5);

        ProductDetailsResponse expectedResponse = new ProductDetailsResponse();
        expectedResponse.setQuantity(15);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDetailsResponse(product)).thenReturn(expectedResponse);

        ProductDetailsResponse result = productService.updateStock(1L, request);

        assertThat(result.getQuantity()).isEqualTo(15);
        assertThat(product.getQuantity()).isEqualTo(15);
        verify(productValidator).validateStockAdjustment(10, 5);
    }

    @Test
    void updateStock_shouldDecreaseQuantity() {
        UpdateStockRequest request = new UpdateStockRequest();
        request.setQuantityDelta(-3);

        ProductDetailsResponse expectedResponse = new ProductDetailsResponse();
        expectedResponse.setQuantity(7);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDetailsResponse(product)).thenReturn(expectedResponse);

        ProductDetailsResponse result = productService.updateStock(1L, request);

        assertThat(result.getQuantity()).isEqualTo(7);
        assertThat(product.getQuantity()).isEqualTo(7);
    }

    @Test
    void updateStock_shouldThrowBadRequestWhenInsufficient() {
        UpdateStockRequest request = new UpdateStockRequest();
        request.setQuantityDelta(-50);

        doThrow(new BadRequestException("Insufficient stock. Cannot deduct 50 items."))
                .when(productValidator).validateStockAdjustment(10, -50);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> productService.updateStock(1L, request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Insufficient stock");

        verify(productRepository, never()).save(any());
    }

    @Test
    void getAllProducts_shouldReturnPagedResults() {
        ProductFilterRequest filter = new ProductFilterRequest();
        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(productMapper.toResponse(product)).thenReturn(null); // not used for assertion

        var result = productService.getAllProducts(filter, 0, 10);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(1);
    }

    @Test
    void deleteProduct_shouldDeleteWhenFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_shouldThrowNotFoundWhenMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateProduct_shouldUpdateEntityFromDto() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setName("iPhone 15 Pro");
        request.setPrice(new BigDecimal("1099.99"));
        request.setCostPrice(new BigDecimal("800.00"));
        request.setMinQuantity(3);

        ProductDetailsResponse expectedResponse = new ProductDetailsResponse();
        expectedResponse.setName("iPhone 15 Pro");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDetailsResponse(product)).thenReturn(expectedResponse);

        ProductDetailsResponse result = productService.updateProduct(1L, request);

        assertThat(result).isNotNull();
        verify(productMapper).updateEntityFromDto(request, product);
        verify(productRepository).save(product);
    }
}