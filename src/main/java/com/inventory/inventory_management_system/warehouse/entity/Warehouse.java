package com.inventory.inventory_management_system.warehouse.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    private String location;
    private String address;
    private String managerName;
    private String phone;

    private Integer capacity;

    @Builder.Default
    private Boolean active = true;
}
