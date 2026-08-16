package com.inventory.inventory_management_system.report.exporter;

import com.inventory.inventory_management_system.report.dto.response.ReportResponse;
import com.inventory.inventory_management_system.report.enums.ExportType;
import com.inventory.inventory_management_system.report.exception.ExportException;
import com.inventory.inventory_management_system.report.util.ReportReflectionUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

@Component
public class PdfExporter implements ReportExporter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public byte[] export(Object reportData) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(out);
             PdfDocument pdfDocument = new PdfDocument(writer);
             Document document = new Document(pdfDocument, PageSize.A4)) {

            ReportResponse<?> report = (ReportResponse<?>) reportData;

            // ---- Title ----
            document.add(new Paragraph("Report")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // ---- Metadata ----
            document.add(new Paragraph("Report Type: " + safeString(report.getReportType())));
            document.add(new Paragraph("Generated At: " + formatDateTime(report.getGeneratedAt())));
            document.add(new Paragraph("Period Start: " + formatDateTime(report.getPeriodStart())));
            document.add(new Paragraph("Period End: " + formatDateTime(report.getPeriodEnd())));
            document.add(new Paragraph("\n"));

            // ---- Data ----
            Object data = report.getData();
            if (data != null) {
                Map<String, Object> summaryFields = ReportReflectionUtil.extractScalarFields(data);
                if (!summaryFields.isEmpty()) {
                    document.add(new Paragraph("Summary")
                            .setFontSize(16)
                            .setBold()
                            .setMarginTop(10)
                            .setMarginBottom(5));

                    Table summaryTable = new Table(2);
                    summaryTable.addCell("Field");
                    summaryTable.addCell("Value");
                    for (Map.Entry<String, Object> entry : summaryFields.entrySet()) {
                        summaryTable.addCell(entry.getKey());
                        summaryTable.addCell(safeString(entry.getValue()));
                    }
                    document.add(summaryTable);
                    document.add(new Paragraph("\n"));
                }

                Collection<?> tableRows = ReportReflectionUtil.findFirstCollectionField(data);
                if (tableRows != null && !tableRows.isEmpty()) {
                    document.add(new Paragraph("Data")
                            .setFontSize(16)
                            .setBold()
                            .setMarginTop(10)
                            .setMarginBottom(5));

                    // Build table with dynamic columns
                    java.util.List<Object> firstRow = ReportReflectionUtil.extractScalarFieldsAsList(tableRows.iterator().next());
                    Table dataTable = new Table(firstRow.size());
                    for (int i = 0; i < firstRow.size(); i++) {
                        dataTable.addHeaderCell("Column " + (i + 1));
                    }

                    for (Object row : tableRows) {
                        java.util.List<Object> rowValues = ReportReflectionUtil.extractScalarFieldsAsList(row);
                        for (Object value : rowValues) {
                            dataTable.addCell(safeString(value));
                        }
                    }

                    document.add(dataTable);
                }
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new ExportException("Failed to generate PDF report: " + e.getMessage(), e);
        }
    }

    @Override
    public ExportType getSupportedType() {
        return ExportType.PDF;
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