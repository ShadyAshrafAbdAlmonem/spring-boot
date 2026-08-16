package com.inventory.inventory_management_system.payment.service.impl;

import com.inventory.inventory_management_system.payment.dto.request.CreatePaymentRequest;
import com.inventory.inventory_management_system.payment.dto.request.PaymentFilterRequest;
import com.inventory.inventory_management_system.payment.dto.request.RefundPaymentRequest;
import com.inventory.inventory_management_system.payment.dto.response.PaymentReceiptResponse;
import com.inventory.inventory_management_system.payment.dto.response.PaymentResponse;
import com.inventory.inventory_management_system.payment.entity.Payment;
import com.inventory.inventory_management_system.payment.entity.PaymentMethod;
import com.inventory.inventory_management_system.payment.mapper.PaymentMapper;
import com.inventory.inventory_management_system.payment.repository.PaymentRepository;
import com.inventory.inventory_management_system.payment.service.PaymentService;
import com.inventory.inventory_management_system.payment.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentValidator paymentValidator;

    @Override
    public PaymentReceiptResponse processPayment(@NonNull CreatePaymentRequest request) {
        paymentValidator.validateCreate(request);

        Payment payment = paymentMapper.toEntity(request);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        payment.setStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());

        // Dummy resolution of method
        PaymentMethod method = PaymentMethod.builder()
                .id(request.getPaymentMethodId())
                .name("CREDIT_CARD")
                .code("CC")
                .active(true)
                .build();
        payment.setPaymentMethod(method);

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toReceiptResponse(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(@NonNull Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentReceiptResponse getReceipt(@NonNull Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return paymentMapper.toReceiptResponse(payment);
    }

    @Override
    public PaymentResponse refundPayment(@NonNull RefundPaymentRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + request.getPaymentId()));

        paymentValidator.validateRefund(payment, request);

        payment.setStatus("REFUNDED");
        payment.setNotes("Refunded: " + request.getReason());
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> filterPayments(PaymentFilterRequest filter, @NonNull Pageable pageable) {
        if (filter.getCustomerId() != null) {
            return paymentRepository.findByCustomerId(filter.getCustomerId(), pageable)
                    .map(paymentMapper::toResponse);
        }
        return paymentRepository.findAll(pageable).map(paymentMapper::toResponse);
    }
}
