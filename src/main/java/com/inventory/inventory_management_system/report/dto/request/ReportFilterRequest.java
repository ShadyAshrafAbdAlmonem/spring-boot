package com.inventory.inventory_management_system.report.dto.request;

import com.inventory.inventory_management_system.report.enums.ExportType;
import com.inventory.inventory_management_system.report.enums.ReportPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportFilterRequest {
    @Builder.Default
    private ReportPeriod period = ReportPeriod.MONTHLY;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Builder.Default
    private ExportType exportType = ExportType.JSON;
}