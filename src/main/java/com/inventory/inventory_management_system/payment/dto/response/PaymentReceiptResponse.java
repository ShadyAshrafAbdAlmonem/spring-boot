package com.inventory.inventory_management_system.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReceiptResponse {
    private String receiptNumber;
    private String transactionId;
    private Long invoiceId;
    private BigDecimal amountPaid;
    private String paymentMethod;
    private LocalDateTime timestamp;
    private String status;
    private String notes;
}
