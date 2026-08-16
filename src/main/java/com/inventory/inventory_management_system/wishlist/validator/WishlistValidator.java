package com.inventory.inventory_management_system.wishlist.validator;

import lombok.RequiredArgsConstructor;
import com.inventory.inventory_management_system.wishlist.dto.request.AddToWishlistRequest;
import com.inventory.inventory_management_system.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishlistValidator {

    private final WishlistRepository wishlistRepository;

    public void validateAddItem(Long userId, AddToWishlistRequest request) {
        if (request.getProductId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        // Check if the product already exists in the user's wishlist
        wishlistRepository.findByUserId(userId).ifPresent(wishlist -> {
            boolean alreadyExists = wishlist.getItems().stream()
                    .anyMatch(item -> request.getProductId().equals(item.getProductId()));
            
            if (alreadyExists) {
                throw new IllegalStateException("Product is already in your wishlist");
            }
        });
    }
}
