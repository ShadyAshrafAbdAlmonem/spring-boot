package com.inventory.inventory_management_system.wishlist.controller;

import com.inventory.inventory_management_system.wishlist.dto.request.AddToWishlistRequest;
import com.inventory.inventory_management_system.wishlist.dto.request.RemoveWishlistItemRequest;
import com.inventory.inventory_management_system.wishlist.dto.response.WishlistResponse;
import com.inventory.inventory_management_system.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<WishlistResponse> getWishlist(@PathVariable @NonNull Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userId));
    }

    @PostMapping("/user/{userId}/items")
    public ResponseEntity<WishlistResponse> addItem(
            @PathVariable @NonNull Long userId,
            @Valid @RequestBody @NonNull AddToWishlistRequest request) {
        return ResponseEntity.ok(wishlistService.addItemToWishlist(userId, request));
    }

    @DeleteMapping("/user/{userId}/items")
    public ResponseEntity<WishlistResponse> removeItem(
            @PathVariable @NonNull Long userId,
            @Valid @RequestBody @NonNull RemoveWishlistItemRequest request) {
        return ResponseEntity.ok(wishlistService.removeItemFromWishlist(userId, request));
    }
}