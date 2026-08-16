package com.inventory.inventory_management_system.payment.validator;

import com.inventory.inventory_management_system.payment.dto.request.CreatePaymentRequest;
import com.inventory.inventory_management_system.payment.dto.request.RefundPaymentRequest;
import com.inventory.inventory_management_system.payment.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {

    public void validateCreate(CreatePaymentRequest request) {
        if (request.getAmount() == null || request.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
    }

    public void validateRefund(Payment payment, RefundPaymentRequest request) {
        if (!"COMPLETED".equalsIgnoreCase(payment.getStatus())) {
            throw new IllegalStateException("Only completed payments can be refunded");
        }
        if (request.getRefundAmount().compareTo(payment.getAmount()) > 0) {
            throw new IllegalArgumentException("Refund amount cannot exceed original payment amount");
        }
    }
}
