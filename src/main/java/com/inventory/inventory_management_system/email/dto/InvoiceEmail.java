package com.inventory.inventory_management_system.email.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceEmail {
    private String recipientEmail;
    private String invoiceNumber;
    private BigDecimal amount;
    private String pdfUrl;
}
