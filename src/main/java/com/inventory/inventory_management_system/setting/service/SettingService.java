package com.inventory.inventory_management_system.setting.service;

import com.inventory.inventory_management_system.setting.dto.request.UpdateSettingRequest;
import com.inventory.inventory_management_system.setting.dto.response.SettingResponse;

import java.util.List;

public interface SettingService {
    List<SettingResponse> getAllSettings();
    List<SettingResponse> getSettingsByGroup(String group);
    SettingResponse getSettingByKey(String key);
    SettingResponse updateSetting(UpdateSettingRequest request);
}
