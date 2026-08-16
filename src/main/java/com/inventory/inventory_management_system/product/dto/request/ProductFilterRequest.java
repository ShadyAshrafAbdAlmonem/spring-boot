package com.inventory.inventory_management_system.product.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductFilterRequest {
    private String name;
    private String category;
    private String brand;
    private Boolean lowStock; // جلب المنتجات التي وصل حجمها أقل من minQuantity
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean active;
}