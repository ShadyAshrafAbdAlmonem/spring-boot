package com.inventory.inventory_management_system.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    private String barcode;
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Cost price is required")
    @PositiveOrZero(message = "Cost price must be positive or zero")
    private BigDecimal costPrice;

    @NotNull(message = "Minimum quantity is required")
    @PositiveOrZero(message = "Minimum quantity must be positive or zero")
    private Integer minQuantity;

    private String category;
    private String brand;
    private String imageUrl;
}