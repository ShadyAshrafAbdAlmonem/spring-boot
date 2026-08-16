package com.inventory.inventory_management_system.report.util;

import com.inventory.inventory_management_system.report.enums.ExportType;
import com.inventory.inventory_management_system.report.enums.ReportType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileNameGenerator {

    public String generateFileName(ReportType reportType, ExportType exportType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String extension = exportType.name().toLowerCase();
        return String.format("%s_report_%s.%s", reportType.name().toLowerCase(), timestamp, extension);
    }
}