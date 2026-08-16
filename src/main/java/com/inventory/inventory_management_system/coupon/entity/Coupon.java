package com.inventory.inventory_management_system.coupon.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String description;

    @Column(name = "discount_type")
    private String discountType; // e.g., PERCENTAGE, FIXED_AMOUNT

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "min_purchase")
    private BigDecimal minOrderAmount;

    @Column(name = "max_discount")
    private BigDecimal maxDiscount;

    private Integer maxUses;

    @Builder.Default
    private Integer currentUses = 0;

    @Column(name = "valid_from")
    private LocalDateTime startDate;

    @Column(name = "valid_until")
    private LocalDateTime endDate;

    private Boolean active;
}
