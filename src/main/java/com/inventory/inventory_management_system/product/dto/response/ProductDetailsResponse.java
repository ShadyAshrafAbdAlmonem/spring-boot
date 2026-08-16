package com.inventory.inventory_management_system.product.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDetailsResponse {
    private Long id;
    private String name;
    private String sku;
    private String barcode;
    private String qrCode;
    private String description;
    private BigDecimal price;
    private BigDecimal costPrice;
    private Integer quantity;
    private Integer minQuantity;
    private String category;
    private String brand;
    private String imageUrl;
    private Boolean active;
}
