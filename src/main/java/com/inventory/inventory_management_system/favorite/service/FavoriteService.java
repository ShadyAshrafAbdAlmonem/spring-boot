package com.inventory.inventory_management_system.favorite.service;

import com.inventory.inventory_management_system.favorite.dto.request.AddFavoriteRequest;
import com.inventory.inventory_management_system.favorite.dto.request.RemoveFavoriteRequest;
import com.inventory.inventory_management_system.favorite.dto.response.FavoriteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {
    FavoriteResponse addFavorite(AddFavoriteRequest request);
    void removeFavorite(RemoveFavoriteRequest request);
    Page<FavoriteResponse> getCustomerFavorites(Long customerId, Pageable pageable);
    boolean isFavorite(Long customerId, Long productId);
}
