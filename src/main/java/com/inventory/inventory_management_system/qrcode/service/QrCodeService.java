package com.inventory.inventory_management_system.qrcode.service;

public interface QrCodeService {
    byte[] generateProductQrCode(Long productId);
    byte[] generateInvoiceQrCode(String invoiceNumber);
    byte[] generateCustomQrCode(String text, int width, int height);
}
