package com.inventory.inventory_management_system.setting.service.impl;

import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.setting.dto.request.UpdateSettingRequest;
import com.inventory.inventory_management_system.setting.dto.response.SettingResponse;
import com.inventory.inventory_management_system.setting.entity.Setting;
import com.inventory.inventory_management_system.setting.mapper.SettingMapper;
import com.inventory.inventory_management_system.setting.repository.SettingRepository;
import com.inventory.inventory_management_system.setting.service.SettingService;
import com.inventory.inventory_management_system.setting.validator.SettingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;
    private final SettingMapper settingMapper;
    private final SettingValidator settingValidator;

    @Override
    @Transactional(readOnly = true)
    public List<SettingResponse> getAllSettings() {
        return settingRepository.findAll().stream()
                .map(settingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SettingResponse> getSettingsByGroup(String group) {
        return settingRepository.findByGroup(group).stream()
                .map(settingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SettingResponse getSettingByKey(String key) {
        Setting setting = settingRepository.findByKey(key)
                .orElseThrow(() -> new ResourceNotFoundException("Setting not found with key: " + key));
        return settingMapper.toResponse(setting);
    }

    @Override
    @Transactional
    public SettingResponse updateSetting(UpdateSettingRequest request) {
        settingValidator.validateUpdateSetting(request);

        Setting setting = settingRepository.findByKey(request.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("Setting not found with key: " + request.getKey()));

        setting.setValue(request.getValue());
        Setting updatedSetting = settingRepository.save(setting);

        return settingMapper.toResponse(updatedSetting);
    }
}
