package com.inventory.inventory_management_system.activitylog.controller;

import com.inventory.inventory_management_system.activitylog.dto.request.ActivityLogFilterRequest;
import com.inventory.inventory_management_system.activitylog.dto.response.ActivityLogResponse;
import com.inventory.inventory_management_system.activitylog.service.ActivityLogService;
import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/activity-logs")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ActivityLogResponse>>> getLogs(
            @ModelAttribute @NonNull ActivityLogFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<ActivityLogResponse> logs = activityLogService.getLogs(filter, page, size);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }
}
