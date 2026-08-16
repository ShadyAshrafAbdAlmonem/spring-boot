package com.inventory.inventory_management_system.discount.service.impl;

import com.inventory.inventory_management_system.discount.dto.request.ApplyDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.request.CreateDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.request.UpdateDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.response.DiscountDetailsResponse;
import com.inventory.inventory_management_system.discount.dto.response.DiscountResponse;
import com.inventory.inventory_management_system.discount.entity.Discount;
import com.inventory.inventory_management_system.discount.mapper.DiscountMapper;
import com.inventory.inventory_management_system.discount.repository.DiscountRepository;
import com.inventory.inventory_management_system.discount.service.DiscountService;
import com.inventory.inventory_management_system.discount.specification.DiscountSpecification;
import com.inventory.inventory_management_system.discount.validator.DiscountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final DiscountValidator discountValidator;

    @Override
    public DiscountResponse createDiscount(@NonNull CreateDiscountRequest request) {
        discountValidator.validateCreate(request);
        Discount discount = discountMapper.toEntity(request);
        return discountMapper.toResponse(discountRepository.save(discount));
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountResponse getDiscountById(@NonNull Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + id));
        return discountMapper.toResponse(discount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiscountResponse> getDiscounts(String name, Boolean active, @NonNull Pageable pageable) {
        Specification<Discount> spec = Specification.where(DiscountSpecification.hasNameLike(name))
                .and(DiscountSpecification.isActive(active));
        return discountRepository.findAll(spec, pageable).map(discountMapper::toResponse);
    }

    @Override
    public DiscountResponse updateDiscount(@NonNull Long id, @NonNull UpdateDiscountRequest request) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + id));

        discountMapper.updateEntityFromDto(request, discount);
        return discountMapper.toResponse(discountRepository.save(discount));
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountDetailsResponse calculateDiscount(ApplyDiscountRequest request) {
        Discount discount = discountRepository.findById(request.getDiscountId())
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + request.getDiscountId()));

        try {
            discountValidator.validateApply(discount, request);
        } catch (Exception e) {
            return DiscountDetailsResponse.builder()
                    .discountId(discount.getId())
                    .discountName(discount.getName())
                    .originalAmount(request.getOriginalAmount())
                    .discountAmount(BigDecimal.ZERO)
                    .finalAmount(request.getOriginalAmount())
                    .applicable(false)
                    .message(e.getMessage())
                    .build();
        }

        BigDecimal discountAmount;
        if ("PERCENTAGE".equalsIgnoreCase(discount.getDiscountType())) {
            discountAmount = request.getOriginalAmount()
                    .multiply(discount.getDiscountValue())
                    .divide(BigDecimal.valueOf(100));
        } else {
            discountAmount = discount.getDiscountValue();
        }

        BigDecimal finalAmount = request.getOriginalAmount().subtract(discountAmount).max(BigDecimal.ZERO);

        return DiscountDetailsResponse.builder()
                .discountId(discount.getId())
                .discountName(discount.getName())
                .originalAmount(request.getOriginalAmount())
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .applicable(true)
                .message("Discount applied successfully")
                .build();
    }

    @Override
    public void deleteDiscount(@NonNull Long id) {
        if (!discountRepository.existsById(id)) {
            throw new RuntimeException("Discount not found with id: " + id);
        }
        discountRepository.deleteById(id);
    }
}