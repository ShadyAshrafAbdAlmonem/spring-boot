package com.inventory.inventory_management_system.wishlist.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class WishlistResponse {
    private Long id;
    private Long userId;
    private List<WishlistItemResponse> items;
}
