package com.inventory.inventory_management_system.product.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductSummaryResponse {
    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
}