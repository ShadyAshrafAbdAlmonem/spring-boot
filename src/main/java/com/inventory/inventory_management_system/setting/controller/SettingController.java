package com.inventory.inventory_management_system.setting.controller;

import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.setting.dto.request.UpdateSettingRequest;
import com.inventory.inventory_management_system.setting.dto.response.SettingResponse;
import com.inventory.inventory_management_system.setting.service.SettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SettingResponse>>> getAllSettings() {
        return ResponseEntity.ok(ApiResponse.success(settingService.getAllSettings()));
    }

    @GetMapping("/group/{group}")
    public ResponseEntity<ApiResponse<List<SettingResponse>>> getSettingsByGroup(@PathVariable String group) {
        return ResponseEntity.ok(ApiResponse.success(settingService.getSettingsByGroup(group.toUpperCase())));
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<ApiResponse<SettingResponse>> getSettingByKey(@PathVariable String key) {
        return ResponseEntity.ok(ApiResponse.success(settingService.getSettingByKey(key)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<SettingResponse>> updateSetting(@Valid @RequestBody UpdateSettingRequest request) {
        SettingResponse updated = settingService.updateSetting(request);
        return ResponseEntity.ok(ApiResponse.success(updated, "Setting updated successfully"));
    }
}
