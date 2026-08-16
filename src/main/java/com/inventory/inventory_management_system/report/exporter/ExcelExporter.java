package com.inventory.inventory_management_system.report.exporter;

import com.inventory.inventory_management_system.report.dto.response.ReportResponse;
import com.inventory.inventory_management_system.report.enums.ExportType;
import com.inventory.inventory_management_system.report.exception.ExportException;
import com.inventory.inventory_management_system.report.util.ReportReflectionUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

@Component
public class ExcelExporter implements ReportExporter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public byte[] export(Object reportData) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            ReportResponse<?> report = (ReportResponse<?>) reportData;

            // Create metadata sheet
            Sheet metadataSheet = workbook.createSheet("Metadata");
            createMetadataSheet(workbook, metadataSheet, report);

            // Create data sheet if data exists
            Object data = report.getData();
            if (data != null) {
                Collection<?> tableRows = ReportReflectionUtil.findFirstCollectionField(data);
                if (tableRows != null && !tableRows.isEmpty()) {
                    Sheet dataSheet = workbook.createSheet("Data");
                    createDataSheet(workbook, dataSheet, tableRows);
                }
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new ExportException("Error generating Excel report", e);
        }
    }

    @Override
    public ExportType getSupportedType() {
        return ExportType.EXCEL;
    }

    private void createMetadataSheet(Workbook workbook, Sheet sheet, ReportResponse<?> report) {
        // Header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Field");
        headerRow.createCell(1).setCellValue("Value");
        headerRow.getCell(0).setCellStyle(headerStyle);
        headerRow.getCell(1).setCellStyle(headerStyle);

        // Add metadata
        int rowNum = 1;
        addMetadataRow(sheet, rowNum++, "Report Type", safeString(report.getReportType()));
        addMetadataRow(sheet, rowNum++, "Generated At", formatDateTime(report.getGeneratedAt()));
        addMetadataRow(sheet, rowNum++, "Period Start", formatDateTime(report.getPeriodStart()));
        addMetadataRow(sheet, rowNum++, "Period End", formatDateTime(report.getPeriodEnd()));

        // Add summary fields if data exists
        Object data = report.getData();
        if (data != null) {
            Map<String, Object> summaryFields = ReportReflectionUtil.extractScalarFields(data);
            if (!summaryFields.isEmpty()) {
                addMetadataRow(sheet, rowNum++, "Summary", "");
                for (Map.Entry<String, Object> entry : summaryFields.entrySet()) {
                    addMetadataRow(sheet, rowNum++, entry.getKey(), safeString(entry.getValue()));
                }
            }
        }

        // Auto-size columns
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }

    private void addMetadataRow(Sheet sheet, int rowNum, String field, String value) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(field);
        row.createCell(1).setCellValue(value);
    }

    private void createDataSheet(Workbook workbook, Sheet sheet, Collection<?> rows) {
        if (rows.isEmpty()) {
            return;
        }

        // Header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Create header row from first object's fields
        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        java.util.List<Object> firstRow = ReportReflectionUtil.extractScalarFieldsAsList(rows.iterator().next());
        
        for (int i = 0; i < firstRow.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue("Column " + (i + 1));
            cell.setCellStyle(headerStyle);
        }

        // Create data rows
        for (Object row : rows) {
            Row dataRow = sheet.createRow(rowNum++);
            java.util.List<Object> rowValues = ReportReflectionUtil.extractScalarFieldsAsList(row);
            
            for (int i = 0; i < rowValues.size(); i++) {
                Cell cell = dataRow.createCell(i);
                Object value = rowValues.get(i);
                
                if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue());
                } else if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        // Auto-size all columns
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String formatDateTime(java.time.LocalDateTime dt) {
        return dt != null ? dt.format(FORMATTER) : "";
    }

    private String safeString(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Enum<?>) {
            return ((Enum<?>) value).name();
        }
        return value.toString();
    }
}