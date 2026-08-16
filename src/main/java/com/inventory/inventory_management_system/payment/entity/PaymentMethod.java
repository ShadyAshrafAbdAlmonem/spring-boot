package com.inventory.inventory_management_system.payment.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // e.g., CREDIT_CARD, BANK_TRANSFER, CASH, PAYPAL

    @Column(nullable = false)
    private String name;

    private String description;

    private Boolean active;
}
