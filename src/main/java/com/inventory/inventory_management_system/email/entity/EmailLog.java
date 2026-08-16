package com.inventory.inventory_management_system.email.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailLog extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String templateName;

    @Column(nullable = false)
    private String status; // SENT, FAILED

    @Column(length = 2000)
    private String errorMessage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt;
}
