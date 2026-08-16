package com.inventory.inventory_management_system.discount.validator;

import com.inventory.inventory_management_system.discount.dto.request.ApplyDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.request.CreateDiscountRequest;
import com.inventory.inventory_management_system.discount.entity.Discount;
import com.inventory.inventory_management_system.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DiscountValidator {

    private final DiscountRepository discountRepository;

    public void validateCreate(CreateDiscountRequest request) {
        if (discountRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Discount with name already exists: " + request.getName());
        }
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }

    public void validateApply(Discount discount, ApplyDiscountRequest request) {
        if (!Boolean.TRUE.equals(discount.getActive())) {
            throw new IllegalStateException("Discount is currently inactive");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(discount.getStartDate()) || now.isAfter(discount.getEndDate())) {
            throw new IllegalStateException("Discount is not valid at this time");
        }

        if (discount.getMinPurchaseAmount() != null && request.getOriginalAmount().compareTo(discount.getMinPurchaseAmount()) < 0) {
            throw new IllegalStateException("Minimum purchase amount required: " + discount.getMinPurchaseAmount());
        }
    }
}