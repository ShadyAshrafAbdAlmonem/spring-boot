package com.inventory.inventory_management_system.favorite.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddFavoriteRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Product ID is required")
    private Long productId;
}
