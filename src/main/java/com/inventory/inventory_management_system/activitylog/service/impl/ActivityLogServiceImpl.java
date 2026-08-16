package com.inventory.inventory_management_system.activitylog.service.impl;

import com.inventory.inventory_management_system.activitylog.dto.request.ActivityLogFilterRequest;
import com.inventory.inventory_management_system.activitylog.dto.response.ActivityLogResponse;
import com.inventory.inventory_management_system.activitylog.entity.ActivityLog;
import com.inventory.inventory_management_system.activitylog.mapper.ActivityLogMapper;
import com.inventory.inventory_management_system.activitylog.repository.ActivityLogRepository;
import com.inventory.inventory_management_system.activitylog.service.ActivityLogService;
import com.inventory.inventory_management_system.activitylog.specification.ActivityLogSpecification;
import com.inventory.inventory_management_system.activitylog.validator.ActivityLogValidator;
import com.inventory.inventory_management_system.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository repository;
    private final ActivityLogMapper mapper;
    private final ActivityLogValidator validator;

    @Override
    @Transactional
    public void logActivity(@NonNull ActivityLog activityLog) {
        repository.save(activityLog);
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull PageResponse<ActivityLogResponse> getLogs(@NonNull ActivityLogFilterRequest filter, int page, int size) {
        validator.validateFilterDates(filter);

        Pageable pageable = PageRequest.of(page, size);
        Page<ActivityLog> pageResult = repository.findAll(ActivityLogSpecification.filterLogs(filter), pageable);

        List<ActivityLogResponse> content = pageResult.getContent().stream()
                .map(mapper::toResponse)
                .toList();

        return PageResponse.<ActivityLogResponse>builder()
                .content(content)
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }
}
