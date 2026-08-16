package com.inventory.inventory_management_system.common.enums;

public enum StockMovementType {
    INBOUND,  // توريد شحنة جديدة
    OUTBOUND, // صرف/بيع
    ADJUSTMENT, // تعديل بسبب جرد أو جودة
    RETURN     // مرتجع
}