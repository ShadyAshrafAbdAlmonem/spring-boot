package com.inventory.inventory_management_system.report.exporter;

import com.inventory.inventory_management_system.report.enums.ExportType;
import com.inventory.inventory_management_system.report.exception.ExportException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Factory class to provide the appropriate ReportExporter based on ExportType.
 * Uses dependency injection to manage exporter implementations.
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ReportExporterFactory {

    private final List<ReportExporter> exporters;

    /**
     * Get the appropriate exporter for the given export type.
     *
     * @param exportType The type of export (PDF, EXCEL, CSV, JSON)
     * @return ReportExporter instance that supports the given type
     * @throws ExportException if no exporter is found for the given type
     */
    public ReportExporter getExporter(ExportType exportType) {
        return exporters.stream()
                .filter(exporter -> exporter.getSupportedType() == exportType)
                .findFirst()
                .orElseThrow(() -> new ExportException(
                        "No exporter found for type: " + exportType
                ));
    }

    /**
     * Get all available exporters as a map.
     *
     * @return Map with ExportType as key and ReportExporter as value
     */
    public Map<ExportType, ReportExporter> getAllExporters() {
        return exporters.stream()
                .collect(Collectors.toMap(
                        ReportExporter::getSupportedType,
                        exporter -> exporter
                ));
    }
}
