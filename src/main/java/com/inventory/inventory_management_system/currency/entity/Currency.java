package com.inventory.inventory_management_system.currency.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 3)
    private String code; // e.g., USD, EUR, EGP

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String symbol; // e.g., $, €, E£

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal exchangeRate; // Base currency comparison rate

    @Column(nullable = false)
    private Boolean isDefault;

    @Column(nullable = false)
    private Boolean active;
}
