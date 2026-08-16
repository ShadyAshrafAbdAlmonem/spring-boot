package com.inventory.inventory_management_system.favorite.mapper;

import com.inventory.inventory_management_system.favorite.dto.request.AddFavoriteRequest;
import com.inventory.inventory_management_system.favorite.dto.response.FavoriteResponse;
import com.inventory.inventory_management_system.favorite.entity.Favorite;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FavoriteMapper {

    Favorite toEntity(AddFavoriteRequest request);

    FavoriteResponse toResponse(Favorite entity);
}
