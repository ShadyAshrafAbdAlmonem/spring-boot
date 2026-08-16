package com.inventory.inventory_management_system.report.exporter;

import com.inventory.inventory_management_system.report.enums.ExportType;

public interface ReportExporter {
    
    // إرجاع الملف كـ byte array لتسهيل التنزيل أو الإرسال
    byte[] export(Object reportData);
    
    // معرفة نوع التصدير الخاص بكل Exporter
    ExportType getSupportedType();
}