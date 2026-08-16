package com.inventory.inventory_management_system.address.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String street;
    private String city;
    private String state;

    @Column(name = "postal_code")
    private String zipCode;

    private String country;
}
