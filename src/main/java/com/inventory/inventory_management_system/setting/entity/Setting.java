package com.inventory.inventory_management_system.setting.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "setting_key", nullable = false, unique = true, length = 100)
    private String key; // e.g., "company_name", "default_tax_rate", "currency_code"

    @Column(name = "setting_value", nullable = false, length = 500)
    private String value; // e.g., "My Inventory Co", "14", "EGP"

    @Column(name = "setting_group", nullable = false, length = 50)
    private String group; // e.g., "COMPANY", "TAX", "SYSTEM", "CURRENCY"

    @Column(length = 255)
    private String description;
}