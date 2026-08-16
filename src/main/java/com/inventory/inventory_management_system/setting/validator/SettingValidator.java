package com.inventory.inventory_management_system.setting.validator;

import com.inventory.inventory_management_system.common.exception.BadRequestException;
import com.inventory.inventory_management_system.setting.dto.request.UpdateSettingRequest;
import org.springframework.stereotype.Component;

@Component
public class SettingValidator {

    public void validateUpdateSetting(UpdateSettingRequest request) {
        if (request.getKey() == null || request.getKey().isBlank()) {
            throw new BadRequestException("Setting key cannot be empty");
        }
        if (request.getValue() == null) {
            throw new BadRequestException("Setting value cannot be null");
        }
    }
}
