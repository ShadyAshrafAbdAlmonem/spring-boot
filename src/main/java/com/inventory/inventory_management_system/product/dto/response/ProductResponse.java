package com.inventory.inventory_management_system.product.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String sku;
    private String barcode;
    private BigDecimal price;
    private Integer quantity;
    private String category;
    private String brand;
    private Boolean active;
}