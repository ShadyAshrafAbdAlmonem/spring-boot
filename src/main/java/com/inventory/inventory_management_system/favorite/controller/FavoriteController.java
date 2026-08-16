package com.inventory.inventory_management_system.favorite.controller;

import com.inventory.inventory_management_system.favorite.dto.request.AddFavoriteRequest;
import com.inventory.inventory_management_system.favorite.dto.request.RemoveFavoriteRequest;
import com.inventory.inventory_management_system.favorite.dto.response.FavoriteResponse;
import com.inventory.inventory_management_system.favorite.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<FavoriteResponse> add(@Valid @RequestBody AddFavoriteRequest request) {
        return new ResponseEntity<>(favoriteService.addFavorite(request), HttpStatus.CREATED);
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@Valid @RequestBody RemoveFavoriteRequest request) {
        favoriteService.removeFavorite(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<FavoriteResponse>> getByCustomer(@PathVariable Long customerId, Pageable pageable) {
        return ResponseEntity.ok(favoriteService.getCustomerFavorites(customerId, pageable));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkFavorite(@RequestParam Long customerId, @RequestParam Long productId) {
        return ResponseEntity.ok(favoriteService.isFavorite(customerId, productId));
    }
}
