package com.inventory.inventory_management_system.report.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.inventory_management_system.report.enums.ExportType;
import com.inventory.inventory_management_system.report.exception.ExportException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonExporter implements ReportExporter {

    private final ObjectMapper objectMapper;

    @Override
    public byte[] export(Object reportData) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(reportData);
        } catch (Exception e) {
            throw new ExportException("Error generating JSON report", e);
        }
    }

    @Override
    public ExportType getSupportedType() {
        return ExportType.JSON;
    }
}