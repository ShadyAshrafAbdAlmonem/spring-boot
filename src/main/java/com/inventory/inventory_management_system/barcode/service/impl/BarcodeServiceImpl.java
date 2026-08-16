package com.inventory.inventory_management_system.barcode.service.impl;

import com.inventory.inventory_management_system.barcode.service.BarcodeService;
import com.inventory.inventory_management_system.barcode.util.BarcodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarcodeServiceImpl implements BarcodeService {

    private final BarcodeGenerator barcodeGenerator;

    @Override
    public byte[] generateBarcode(String code) {
        return barcodeGenerator.generateBarcodeImage(code);
    }
}
