package com.inventory.inventory_management_system.report.controller;

import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.report.dto.request.InventoryReportRequest;
import com.inventory.inventory_management_system.report.dto.request.PurchaseReportRequest;
import com.inventory.inventory_management_system.report.dto.request.SalesReportRequest;
import com.inventory.inventory_management_system.report.dto.response.InventoryReportResponse;
import com.inventory.inventory_management_system.report.dto.response.PurchaseReportResponse;
import com.inventory.inventory_management_system.report.dto.response.ReportResponse;
import com.inventory.inventory_management_system.report.dto.response.SalesReportResponse;
import com.inventory.inventory_management_system.report.service.ReportService;
import com.inventory.inventory_management_system.report.util.ReportUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/reports")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<ReportResponse<SalesReportResponse>>> getSalesReport(
            @Valid @ModelAttribute SalesReportRequest request) {
        return ResponseEntity.ok(ApiResponse.success(reportService.generateSalesReport(request)));
    }

    @GetMapping("/sales/export")
    public ResponseEntity<Resource> exportSalesReport(@Valid @ModelAttribute SalesReportRequest request) {
        byte[] fileData = reportService.exportSalesReport(request);
        String fileName = reportService.generateFileName(request.getExportType());

        return ResponseEntity.ok()
                .headers(ReportUtil.createDownloadHeaders(fileName))
                .body(new ByteArrayResource(fileData));
    }

    @GetMapping("/purchases")
    public ResponseEntity<ApiResponse<ReportResponse<PurchaseReportResponse>>> getPurchaseReport(
            @Valid @ModelAttribute PurchaseReportRequest request) {
        return ResponseEntity.ok(ApiResponse.success(reportService.generatePurchaseReport(request)));
    }

    @GetMapping("/inventory")
    public ResponseEntity<ApiResponse<ReportResponse<InventoryReportResponse>>> getInventoryReport(
            @Valid @ModelAttribute InventoryReportRequest request) {
        return ResponseEntity.ok(ApiResponse.success(reportService.generateInventoryReport(request)));
    }
}