package com.inventory.inventory_management_system.discount.service;

import com.inventory.inventory_management_system.discount.dto.request.ApplyDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.request.CreateDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.request.UpdateDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.response.DiscountDetailsResponse;
import com.inventory.inventory_management_system.discount.dto.response.DiscountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface DiscountService {
    DiscountResponse createDiscount(@NonNull CreateDiscountRequest request);
    DiscountResponse getDiscountById(@NonNull Long id);
    Page<DiscountResponse> getDiscounts(@NonNull String name, Boolean active, @NonNull Pageable pageable);
    DiscountResponse updateDiscount(@NonNull Long id, @NonNull UpdateDiscountRequest request);
    DiscountDetailsResponse calculateDiscount(@NonNull ApplyDiscountRequest request);
    void deleteDiscount(@NonNull Long id);
}
