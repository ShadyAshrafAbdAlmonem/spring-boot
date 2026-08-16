package com.inventory.inventory_management_system.favorite.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoriteResponse {
    private Long id;
    private Long customerId;
    private Long productId;
    private LocalDateTime addedAt;
}
