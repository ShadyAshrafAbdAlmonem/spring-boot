package com.inventory.inventory_management_system.wishlist.service;

import com.inventory.inventory_management_system.wishlist.dto.request.AddToWishlistRequest;
import com.inventory.inventory_management_system.wishlist.dto.request.RemoveWishlistItemRequest;
import com.inventory.inventory_management_system.wishlist.dto.response.WishlistResponse;
import org.springframework.lang.NonNull;

public interface WishlistService {
    WishlistResponse getWishlistByUserId(@NonNull Long userId);
    WishlistResponse addItemToWishlist(@NonNull Long userId, @NonNull AddToWishlistRequest request);
    WishlistResponse removeItemFromWishlist(@NonNull Long userId, @NonNull RemoveWishlistItemRequest request);
}
