package com.inventory.inventory_management_system.dashboard.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LowStockDto {
    private Long productId;
    private String productName;
    private String sku;
    private Integer currentStock;
    private Integer threshold;
}
