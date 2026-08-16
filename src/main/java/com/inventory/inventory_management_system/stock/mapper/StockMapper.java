package com.inventory.inventory_management_system.stock.mapper;

import com.inventory.inventory_management_system.stock.dto.response.StockResponse;
import com.inventory.inventory_management_system.stock.entity.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockResponse toResponse(Stock entity);
}