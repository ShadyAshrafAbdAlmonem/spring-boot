package com.inventory.inventory_management_system.report.service;

import com.inventory.inventory_management_system.report.dto.request.InventoryReportRequest;
import com.inventory.inventory_management_system.report.dto.request.PurchaseReportRequest;
import com.inventory.inventory_management_system.report.dto.request.SalesReportRequest;
import com.inventory.inventory_management_system.report.dto.response.InventoryReportResponse;
import com.inventory.inventory_management_system.report.dto.response.PurchaseReportResponse;
import com.inventory.inventory_management_system.report.dto.response.ReportResponse;
import com.inventory.inventory_management_system.report.dto.response.SalesReportResponse;
import com.inventory.inventory_management_system.report.enums.ExportType;

public interface ReportService {

    // 1. المبيعات (Sales)
    ReportResponse<SalesReportResponse> generateSalesReport(SalesReportRequest request);
    byte[] exportSalesReport(SalesReportRequest request);

    // 2. المشتريات (Purchases)
    ReportResponse<PurchaseReportResponse> generatePurchaseReport(PurchaseReportRequest request);
    byte[] exportPurchaseReport(PurchaseReportRequest request);

    // 3. المخزون (Inventory)
    ReportResponse<InventoryReportResponse> generateInventoryReport(InventoryReportRequest request);
    byte[] exportInventoryReport(InventoryReportRequest request);

    // 4. المساعدات (Helper for Controller File Naming)
    String generateFileName(ExportType exportType);
}