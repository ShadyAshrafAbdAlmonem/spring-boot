package com.inventory.inventory_management_system.favorite.validator;

import com.inventory.inventory_management_system.favorite.dto.request.AddFavoriteRequest;
import com.inventory.inventory_management_system.favorite.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteValidator {

    private final FavoriteRepository favoriteRepository;

    public void validateAdd(AddFavoriteRequest request) {
        if (request.getCustomerId() == null || request.getProductId() == null) {
            throw new IllegalArgumentException("Customer ID and Product ID cannot be null");
        }
        if (favoriteRepository.existsByCustomerIdAndProductId(request.getCustomerId(), request.getProductId())) {
            throw new IllegalArgumentException("Product is already in the user's favorites list");
        }
    }
}
