package com.inventory.inventory_management_system.wishlist.service.impl;

import com.inventory.inventory_management_system.wishlist.dto.request.AddToWishlistRequest;
import com.inventory.inventory_management_system.wishlist.dto.request.RemoveWishlistItemRequest;
import com.inventory.inventory_management_system.wishlist.dto.response.WishlistResponse;
import com.inventory.inventory_management_system.wishlist.entity.Wishlist;
import com.inventory.inventory_management_system.wishlist.entity.WishlistItem;
import com.inventory.inventory_management_system.wishlist.mapper.WishlistMapper;
import com.inventory.inventory_management_system.wishlist.repository.WishlistItemRepository;
import com.inventory.inventory_management_system.wishlist.repository.WishlistRepository;
import com.inventory.inventory_management_system.wishlist.service.WishlistService;
import com.inventory.inventory_management_system.wishlist.validator.WishlistValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistMapper wishlistMapper;
    private final WishlistValidator wishlistValidator;

    @Override
    @Transactional(readOnly = true)
    public WishlistResponse getWishlistByUserId(@NonNull Long userId) {
        Wishlist wishlist = getOrCreateWishlist(userId);
        return wishlistMapper.toResponse(wishlist);
    }

    @Override
    public WishlistResponse addItemToWishlist(@NonNull Long userId, @NonNull AddToWishlistRequest request) {
        
        wishlistValidator.validateAddItem(userId, request);

        Wishlist wishlist = getOrCreateWishlist(userId);

        boolean exists = wishlist.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(request.getProductId()));

        if (!exists) {
            WishlistItem item = WishlistItem.builder()
                    .productId(request.getProductId())
                    .wishlist(wishlist)
                    .build();
            wishlist.getItems().add(item);
            wishlist = wishlistRepository.save(wishlist);
        }

        return wishlistMapper.toResponse(wishlist);
    }

    @Override
    public WishlistResponse removeItemFromWishlist(@NonNull Long userId, @NonNull RemoveWishlistItemRequest request) {
        Wishlist wishlist = getOrCreateWishlist(userId);
        wishlist.getItems().removeIf(item -> item.getProductId().equals(request.getProductId()));
        wishlistItemRepository.deleteByWishlistIdAndProductId(wishlist.getId(), request.getProductId());
        return wishlistMapper.toResponse(wishlist);
    }

    @SuppressWarnings("null")
    private Wishlist getOrCreateWishlist(@NonNull Long userId) {
        return wishlistRepository.findByUserId(userId)
                .orElseGet(() -> Objects.requireNonNull(
                        wishlistRepository.save(Wishlist.builder().userId(userId).build()),
                        "Saved wishlist must not be null"));
    }
}
