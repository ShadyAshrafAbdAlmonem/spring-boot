package com.inventory.inventory_management_system.qrcode.service.impl;

import com.inventory.inventory_management_system.qrcode.service.QrCodeService;
import com.inventory.inventory_management_system.qrcode.util.QrCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

    private final QrCodeGenerator qrCodeGenerator;

    @Override
    public byte[] generateProductQrCode(Long productId) {
        String content = "PRODUCT:" + productId;
        return generateCustomQrCode(content, 250, 250);
    }

    @Override
    public byte[] generateInvoiceQrCode(String invoiceNumber) {
        String content = "INVOICE:" + invoiceNumber;
        return generateCustomQrCode(content, 300, 300);
    }

    @Override
    public byte[] generateCustomQrCode(String text, int width, int height) {
        try {
            return qrCodeGenerator.generateQrCodeImage(text, width, height);
        } catch (Exception e) {
            log.error("Error generating QR code for text [{}]: {}", text, e.getMessage(), e);
            throw new RuntimeException("Could not generate QR code image", e);
        }
    }
}
