package com.inventory.inventory_management_system.tax.validator;

import com.inventory.inventory_management_system.tax.dto.request.TaxRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TaxValidator {

    public void validate(TaxRequest request) {
        if (request.getRate() != null && request.getRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative");
        }
    }
}
