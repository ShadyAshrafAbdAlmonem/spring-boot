package com.inventory.inventory_management_system.setting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingResponse {
    private Long id;
    private String key;
    private String value;
    private String group;
    private String description;
}