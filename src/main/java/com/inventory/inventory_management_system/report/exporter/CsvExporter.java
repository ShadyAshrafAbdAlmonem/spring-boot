package com.inventory.inventory_management_system.report.exporter;

import com.inventory.inventory_management_system.report.dto.response.ReportResponse;
import com.inventory.inventory_management_system.report.enums.ExportType;
import com.inventory.inventory_management_system.report.exception.ExportException;
import com.inventory.inventory_management_system.report.util.ReportReflectionUtil;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

@Component
public class CsvExporter implements ReportExporter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public byte[] export(Object reportData) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, org.apache.commons.csv.CSVFormat.DEFAULT)) {

            ReportResponse<?> report = (ReportResponse<?>) reportData;

            // ---- Report metadata header ----
            printer.printRecord("Field", "Value");
            printer.printRecord("Report Type", safeString(report.getReportType()));
            printer.printRecord("Generated At", formatDateTime(report.getGeneratedAt()));
            printer.printRecord("Period Start", formatDateTime(report.getPeriodStart()));
            printer.printRecord("Period End", formatDateTime(report.getPeriodEnd()));
            printer.println();

            // ---- Summary fields (non-collection properties of the data object) ----
            Object data = report.getData();
            if (data != null) {
                Map<String, Object> summaryFields = ReportReflectionUtil.extractScalarFields(data);
                printer.printRecord("Summary", "");
                for (Map.Entry<String, Object> entry : summaryFields.entrySet()) {
                    printer.printRecord(entry.getKey(), safeString(entry.getValue()));
                }
                printer.println();

                // ---- Table rows from the first Collection field ----
                Collection<?> tableRows = ReportReflectionUtil.findFirstCollectionField(data);
                if (tableRows != null && !tableRows.isEmpty()) {
                    // Header row
                    java.util.List<Object> firstRow = ReportReflectionUtil.extractScalarFieldsAsList(tableRows.iterator().next());
                    printer.printRecord(firstRow.toArray());

                    // Data rows
                    for (Object row : tableRows) {
                        java.util.List<Object> rowValues = ReportReflectionUtil.extractScalarFieldsAsList(row);
                        printer.printRecord(rowValues.toArray());
                    }
                }
            }

            writer.flush();
            return out.toByteArray();

        } catch (Exception e) {
            throw new ExportException("Error generating CSV report", e);
        }
    }

    @Override
    public ExportType getSupportedType() {
        return ExportType.CSV;
    }

    private static String formatDateTime(java.time.LocalDateTime dt) {
        return dt != null ? dt.format(FORMATTER) : "";
    }

    private static String safeString(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Enum<?>) {
            return ((Enum<?>) value).name();
        }
        return value.toString();
    }
}
