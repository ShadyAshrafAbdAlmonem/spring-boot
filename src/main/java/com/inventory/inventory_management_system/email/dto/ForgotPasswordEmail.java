package com.inventory.inventory_management_system.email.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgotPasswordEmail {
    private String recipientEmail;
    private String resetToken;
    private String resetUrl;
}
