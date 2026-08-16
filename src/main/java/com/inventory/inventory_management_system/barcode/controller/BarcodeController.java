package com.inventory.inventory_management_system.barcode.controller;

import com.inventory.inventory_management_system.barcode.service.BarcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/barcodes")
@RequiredArgsConstructor
public class BarcodeController {

    private final BarcodeService barcodeService;

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateBarcode(@RequestParam String code) {
        byte[] image = barcodeService.generateBarcode(code);
        return ResponseEntity.ok(image);
    }
}