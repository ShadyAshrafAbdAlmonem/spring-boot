package com.inventory.inventory_management_system.payment.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Long id;
    private String transactionId;
    private Long invoiceId;
    private Long customerId;
    private String paymentMethodName;
    private BigDecimal amount;
    private String status;
    private LocalDateTime paymentDate;
}
