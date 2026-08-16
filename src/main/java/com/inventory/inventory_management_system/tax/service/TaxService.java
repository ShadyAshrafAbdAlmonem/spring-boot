package com.inventory.inventory_management_system.tax.service;

import com.inventory.inventory_management_system.tax.dto.request.TaxRequest;
import com.inventory.inventory_management_system.tax.dto.response.TaxResponse;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TaxService {
    TaxResponse createTax(@NonNull TaxRequest request);
    TaxResponse getTaxById(@NonNull Long id);
    List<TaxResponse> getAllTaxes();
    TaxResponse updateTax(@NonNull Long id, @NonNull TaxRequest request);
    void deleteTax(@NonNull Long id);
}
