package com.inventory.inventory_management_system.tax.service.impl;

import com.inventory.inventory_management_system.tax.dto.request.TaxRequest;
import com.inventory.inventory_management_system.tax.dto.response.TaxResponse;
import com.inventory.inventory_management_system.tax.entity.Tax;
import com.inventory.inventory_management_system.tax.mapper.TaxMapper;
import com.inventory.inventory_management_system.tax.repository.TaxRepository;
import com.inventory.inventory_management_system.tax.service.TaxService;
import com.inventory.inventory_management_system.tax.validator.TaxValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.lang.NonNull;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;
    private final TaxMapper taxMapper;
    private final TaxValidator taxValidator;

    @Override
    public TaxResponse createTax(@NonNull TaxRequest request) {
        taxValidator.validate(request);
        Tax tax = taxMapper.toEntity(request);
        return taxMapper.toResponse(taxRepository.save(tax));
    }

    @Override
    @Transactional(readOnly = true)
    public TaxResponse getTaxById(Long id) {
        Tax tax = taxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax record not found with id: " + id));
        return taxMapper.toResponse(tax);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxResponse> getAllTaxes() {
        return taxRepository.findAll().stream()
                .map(taxMapper::toResponse)
                .toList();
    }

    @Override
    public TaxResponse updateTax(Long id, TaxRequest request) {
        taxValidator.validate(request);
        Tax tax = taxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax record not found with id: " + id));
        taxMapper.updateEntityFromDto(request, tax);
        return taxMapper.toResponse(taxRepository.save(tax));
    }

    @Override
    public void deleteTax(Long id) {
        if (!taxRepository.existsById(id)) {
            throw new RuntimeException("Tax record not found with id: " + id);
        }
        taxRepository.deleteById(id);
    }
}
