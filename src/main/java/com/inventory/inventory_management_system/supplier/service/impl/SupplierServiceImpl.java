package com.inventory.inventory_management_system.supplier.service.impl;

import com.inventory.inventory_management_system.supplier.dto.request.CreateSupplierRequest;
import com.inventory.inventory_management_system.supplier.dto.request.UpdateSupplierRequest;
import com.inventory.inventory_management_system.supplier.dto.response.SupplierResponse;
import com.inventory.inventory_management_system.supplier.entity.Supplier;
import com.inventory.inventory_management_system.supplier.mapper.SupplierMapper;
import com.inventory.inventory_management_system.supplier.repository.SupplierRepository;
import com.inventory.inventory_management_system.supplier.service.SupplierService;
import com.inventory.inventory_management_system.supplier.specification.SupplierSpecification;
import com.inventory.inventory_management_system.supplier.validator.SupplierValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final SupplierValidator supplierValidator;

    @Override
    public SupplierResponse createSupplier(@NonNull CreateSupplierRequest request) {
        supplierValidator.validateCreate(request);
        Supplier supplier = supplierMapper.toEntity(request);
        supplier.setActive(true);
        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        return supplierMapper.toResponse(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplierResponse> searchSuppliers(String name, Boolean active, Pageable pageable) {
        Specification<Supplier> spec = Specification.where(SupplierSpecification.hasNameLike(name))
                .and(SupplierSpecification.isActive(active));
        return supplierRepository.findAll(spec, pageable).map(supplierMapper::toResponse);
    }

    @Override
    public SupplierResponse updateSupplier(Long id, UpdateSupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        supplierMapper.updateEntityFromDto(request, supplier);
        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}