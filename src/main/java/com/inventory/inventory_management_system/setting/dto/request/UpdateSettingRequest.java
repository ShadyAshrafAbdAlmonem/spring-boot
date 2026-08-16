package com.inventory.inventory_management_system.setting.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSettingRequest {

    @NotBlank(message = "Setting key is required")
    private String key;

    @NotBlank(message = "Setting value is required")
    private String value;
}