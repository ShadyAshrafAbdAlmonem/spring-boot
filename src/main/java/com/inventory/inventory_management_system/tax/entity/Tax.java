package com.inventory.inventory_management_system.tax.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tax_rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tax extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal rate;
    private String country;
    private String state;
    private Boolean active;
}
