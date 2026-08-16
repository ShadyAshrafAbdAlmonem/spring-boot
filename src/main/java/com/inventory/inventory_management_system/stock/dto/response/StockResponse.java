package com.inventory.inventory_management_system.stock.dto.response;

import lombok.Data;

@Data
public class StockResponse {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Integer quantity;
}
