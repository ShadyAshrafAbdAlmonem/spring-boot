package com.inventory.inventory_management_system.wishlist.mapper;

import com.inventory.inventory_management_system.wishlist.dto.response.WishlistItemResponse;
import com.inventory.inventory_management_system.wishlist.dto.response.WishlistResponse;
import com.inventory.inventory_management_system.wishlist.entity.Wishlist;
import com.inventory.inventory_management_system.wishlist.entity.WishlistItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    WishlistResponse toResponse(Wishlist entity);

    WishlistItemResponse toItemResponse(WishlistItem item);
}
