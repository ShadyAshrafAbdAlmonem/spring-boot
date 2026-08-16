package com.inventory.inventory_management_system.activitylog.validator;

import com.inventory.inventory_management_system.activitylog.dto.request.ActivityLogFilterRequest;
import com.inventory.inventory_management_system.common.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ActivityLogValidator {
    public void validateFilterDates(ActivityLogFilterRequest filter) {
        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            if (filter.getStartDate().isAfter(filter.getEndDate())) {
                throw new BadRequestException("Start date cannot be after end date");
            }
        }
    }
}
