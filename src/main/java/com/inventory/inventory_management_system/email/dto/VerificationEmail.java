package com.inventory.inventory_management_system.email.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationEmail {
    private String recipientEmail;
    private String verificationCode;
    private String verificationUrl;
}