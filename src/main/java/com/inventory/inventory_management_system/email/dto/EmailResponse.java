package com.inventory.inventory_management_system.email.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponse {
    private Long id;
    private String recipient;
    private String subject;
    private String status;
    private LocalDateTime sentAt;
}
