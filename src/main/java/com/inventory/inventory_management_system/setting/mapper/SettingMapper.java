package com.inventory.inventory_management_system.setting.mapper;

import com.inventory.inventory_management_system.setting.dto.response.SettingResponse;
import com.inventory.inventory_management_system.setting.entity.Setting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SettingMapper {
    SettingResponse toResponse(Setting setting);
}
