package com.inventory.inventory_management_system.supplier.service;

import com.inventory.inventory_management_system.supplier.dto.request.CreateSupplierRequest;
import com.inventory.inventory_management_system.supplier.dto.request.UpdateSupplierRequest;
import com.inventory.inventory_management_system.supplier.dto.response.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface SupplierService {
    SupplierResponse createSupplier(@NonNull CreateSupplierRequest request);
    SupplierResponse getSupplierById(@NonNull Long id);
    Page<SupplierResponse> searchSuppliers(String name, Boolean active, @NonNull Pageable pageable);
    SupplierResponse updateSupplier(@NonNull Long id, @NonNull UpdateSupplierRequest request);
    void deleteSupplier(@NonNull Long id);
}
