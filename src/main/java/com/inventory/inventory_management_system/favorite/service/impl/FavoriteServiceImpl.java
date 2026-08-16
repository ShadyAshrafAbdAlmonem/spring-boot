package com.inventory.inventory_management_system.favorite.service.impl;

import com.inventory.inventory_management_system.favorite.dto.request.AddFavoriteRequest;
import com.inventory.inventory_management_system.favorite.dto.request.RemoveFavoriteRequest;
import com.inventory.inventory_management_system.favorite.dto.response.FavoriteResponse;
import com.inventory.inventory_management_system.favorite.entity.Favorite;
import com.inventory.inventory_management_system.favorite.mapper.FavoriteMapper;
import com.inventory.inventory_management_system.favorite.repository.FavoriteRepository;
import com.inventory.inventory_management_system.favorite.service.FavoriteService;
import com.inventory.inventory_management_system.favorite.validator.FavoriteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;
    private final FavoriteValidator favoriteValidator;

    @Override
    public FavoriteResponse addFavorite(AddFavoriteRequest request) {
        favoriteValidator.validateAdd(request);

        Favorite favorite = favoriteMapper.toEntity(request);
        favorite.setAddedAt(LocalDateTime.now());

        return favoriteMapper.toResponse(favoriteRepository.save(favorite));
    }

    @Override
    public void removeFavorite(RemoveFavoriteRequest request) {
        if (!favoriteRepository.existsByCustomerIdAndProductId(request.getCustomerId(), request.getProductId())) {
            throw new RuntimeException("Favorite record not found");
        }
        favoriteRepository.deleteByCustomerIdAndProductId(request.getCustomerId(), request.getProductId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FavoriteResponse> getCustomerFavorites(Long customerId, Pageable pageable) {
        return favoriteRepository.findByCustomerId(customerId, pageable)
                .map(favoriteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavorite(Long customerId, Long productId) {
        return favoriteRepository.existsByCustomerIdAndProductId(customerId, productId);
    }
}
