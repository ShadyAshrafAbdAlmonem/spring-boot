package com.inventory.inventory_management_system.report.service.impl;

import com.inventory.inventory_management_system.report.dto.request.InventoryReportRequest;
import com.inventory.inventory_management_system.report.dto.request.PurchaseReportRequest;
import com.inventory.inventory_management_system.report.dto.request.SalesReportRequest;
import com.inventory.inventory_management_system.report.dto.response.InventoryReportResponse;
import com.inventory.inventory_management_system.report.dto.response.PurchaseReportResponse;
import com.inventory.inventory_management_system.report.dto.response.ReportResponse;
import com.inventory.inventory_management_system.report.dto.response.SalesReportResponse;
import com.inventory.inventory_management_system.report.enums.ExportType;
import com.inventory.inventory_management_system.report.enums.ReportType;
import com.inventory.inventory_management_system.report.exporter.ReportExporterFactory;
import com.inventory.inventory_management_system.report.service.ReportService;
import com.inventory.inventory_management_system.report.util.DateRangeUtil;
import com.inventory.inventory_management_system.report.util.FileNameGenerator;
import com.inventory.inventory_management_system.report.validator.ReportValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportValidator reportValidator;
    private final ReportExporterFactory exporterFactory;
    private final FileNameGenerator fileNameGenerator;

    // ==================== SALES REPORT ====================

    @Override
    public ReportResponse<SalesReportResponse> generateSalesReport(SalesReportRequest request) {
        reportValidator.validateFilter(request);

        LocalDateTime start = DateRangeUtil.getStartDateTime(request.getPeriod(), request.getStartDate());
        LocalDateTime end = DateRangeUtil.getEndDateTime(request.getPeriod(), request.getEndDate());

        // يمكنك استبدال هذا المنطق باستعلامات الـ JPA أو Native Queries الفعلية
        SalesReportResponse salesData = SalesReportResponse.builder()
                .totalOrders(0)
                .totalItemsSold(0)
                .totalRevenue(java.math.BigDecimal.ZERO)
                .averageOrderValue(java.math.BigDecimal.ZERO)
                .items(Collections.emptyList())
                .build();

        return ReportResponse.<SalesReportResponse>builder()
                .reportType(ReportType.SALES)
                .generatedAt(LocalDateTime.now())
                .periodStart(start)
                .periodEnd(end)
                .data(salesData)
                .build();
    }

    @Override
    public byte[] exportSalesReport(SalesReportRequest request) {
        ReportResponse<SalesReportResponse> report = generateSalesReport(request);
        return exporterFactory.getExporter(request.getExportType()).export(report);
    }

    // ==================== PURCHASE REPORT ====================

    @Override
    public ReportResponse<PurchaseReportResponse> generatePurchaseReport(PurchaseReportRequest request) {
        reportValidator.validateFilter(request);

        LocalDateTime start = DateRangeUtil.getStartDateTime(request.getPeriod(), request.getStartDate());
        LocalDateTime end = DateRangeUtil.getEndDateTime(request.getPeriod(), request.getEndDate());

        PurchaseReportResponse purchaseData = PurchaseReportResponse.builder()
                .totalPurchaseOrders(0)
                .totalSpent(java.math.BigDecimal.ZERO)
                .supplierSummaries(Collections.emptyList())
                .build();

        return ReportResponse.<PurchaseReportResponse>builder()
                .reportType(ReportType.PURCHASE)
                .generatedAt(LocalDateTime.now())
                .periodStart(start)
                .periodEnd(end)
                .data(purchaseData)
                .build();
    }

    @Override
    public byte[] exportPurchaseReport(PurchaseReportRequest request) {
        ReportResponse<PurchaseReportResponse> report = generatePurchaseReport(request);
        return exporterFactory.getExporter(request.getExportType()).export(report);
    }

    // ==================== INVENTORY REPORT ====================

    @Override
    public ReportResponse<InventoryReportResponse> generateInventoryReport(InventoryReportRequest request) {
        reportValidator.validateFilter(request);

        LocalDateTime start = DateRangeUtil.getStartDateTime(request.getPeriod(), request.getStartDate());
        LocalDateTime end = DateRangeUtil.getEndDateTime(request.getPeriod(), request.getEndDate());

        InventoryReportResponse inventoryData = InventoryReportResponse.builder()
                .totalProducts(0)
                .totalQuantityInStock(0)
                .lowStockCount(0)
                .outOfStockCount(0)
                .totalInventoryValue(java.math.BigDecimal.ZERO)
                .categories(Collections.emptyList())
                .build();

        return ReportResponse.<InventoryReportResponse>builder()
                .reportType(ReportType.INVENTORY)
                .generatedAt(LocalDateTime.now())
                .periodStart(start)
                .periodEnd(end)
                .data(inventoryData)
                .build();
    }

    @Override
    public byte[] exportInventoryReport(InventoryReportRequest request) {
        ReportResponse<InventoryReportResponse> report = generateInventoryReport(request);
        return exporterFactory.getExporter(request.getExportType()).export(report);
    }

    // ==================== UTILS ====================

    @Override
    public String generateFileName(ExportType exportType) {
        return fileNameGenerator.generateFileName(ReportType.SALES, exportType);
    }
}