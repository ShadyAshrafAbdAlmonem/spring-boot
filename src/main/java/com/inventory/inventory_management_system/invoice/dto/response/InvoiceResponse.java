package com.inventory.inventory_management_system.invoice.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceResponse {
    private Long id;
    private String invoiceNumber;
    private Long customerId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private String status;
}