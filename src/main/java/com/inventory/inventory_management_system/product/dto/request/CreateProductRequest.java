package com.inventory.inventory_management_system.product.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String barcode;
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Cost price is required")
    @PositiveOrZero(message = "Cost price must be positive or zero")
    private BigDecimal costPrice;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be positive or zero")
    private Integer quantity;

    @NotNull(message = "Minimum quantity is required")
    @PositiveOrZero(message = "Minimum quantity must be positive or zero")
    private Integer minQuantity;

    private String category;
    private String brand;
    private String imageUrl;
}