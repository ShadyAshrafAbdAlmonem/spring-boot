package com.inventory.inventory_management_system.qrcode.controller;

import com.inventory.inventory_management_system.qrcode.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/qrcode")
@RequiredArgsConstructor
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @GetMapping(value = "/product/{productId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getProductQrCode(@PathVariable Long productId) {
        return ResponseEntity.ok(qrCodeService.generateProductQrCode(productId));
    }

    @GetMapping(value = "/invoice/{invoiceNumber}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getInvoiceQrCode(@PathVariable String invoiceNumber) {
        return ResponseEntity.ok(qrCodeService.generateInvoiceQrCode(invoiceNumber));
    }

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(
            @RequestParam String text,
            @RequestParam(defaultValue = "250") int width,
            @RequestParam(defaultValue = "250") int height) {
        return ResponseEntity.ok(qrCodeService.generateCustomQrCode(text, width, height));
    }
}
