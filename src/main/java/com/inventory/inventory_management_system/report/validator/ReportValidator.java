package com.inventory.inventory_management_system.report.validator;

import com.inventory.inventory_management_system.report.dto.request.ReportFilterRequest;
import com.inventory.inventory_management_system.report.enums.ReportPeriod;
import com.inventory.inventory_management_system.report.exception.ReportException;
import org.springframework.stereotype.Component;

@Component
public class ReportValidator {

    public void validateFilter(ReportFilterRequest request) {
        if (request.getPeriod() == ReportPeriod.CUSTOM) {
            if (request.getStartDate() == null || request.getEndDate() == null) {
                throw new ReportException("Start date and End date are required for CUSTOM period.");
            }
            if (request.getStartDate().isAfter(request.getEndDate())) {
                throw new ReportException("Start date cannot be after End date.");
            }
        }
    }
}