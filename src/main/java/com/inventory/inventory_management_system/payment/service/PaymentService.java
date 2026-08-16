package com.inventory.inventory_management_system.payment.service;

import com.inventory.inventory_management_system.payment.dto.request.CreatePaymentRequest;
import com.inventory.inventory_management_system.payment.dto.request.PaymentFilterRequest;
import com.inventory.inventory_management_system.payment.dto.request.RefundPaymentRequest;
import com.inventory.inventory_management_system.payment.dto.response.PaymentReceiptResponse;
import com.inventory.inventory_management_system.payment.dto.response.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface PaymentService {
    PaymentReceiptResponse processPayment(@NonNull CreatePaymentRequest request);
    PaymentResponse getPaymentById(@NonNull Long id);
    PaymentReceiptResponse getReceipt(@NonNull Long id);
    PaymentResponse refundPayment(@NonNull RefundPaymentRequest request);
    Page<PaymentResponse> filterPayments(PaymentFilterRequest filter, @NonNull Pageable pageable);
}
