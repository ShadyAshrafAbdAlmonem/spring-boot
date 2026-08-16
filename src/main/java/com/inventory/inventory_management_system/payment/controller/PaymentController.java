package com.inventory.inventory_management_system.payment.controller;

import com.inventory.inventory_management_system.payment.dto.request.CreatePaymentRequest;
import com.inventory.inventory_management_system.payment.dto.request.PaymentFilterRequest;
import com.inventory.inventory_management_system.payment.dto.request.RefundPaymentRequest;
import com.inventory.inventory_management_system.payment.dto.response.PaymentReceiptResponse;
import com.inventory.inventory_management_system.payment.dto.response.PaymentResponse;
import com.inventory.inventory_management_system.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentReceiptResponse> processPayment(@Valid @RequestBody @NonNull CreatePaymentRequest request) {
        return new ResponseEntity<>(paymentService.processPayment(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/{id}/receipt")
    public ResponseEntity<PaymentReceiptResponse> getReceipt(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(paymentService.getReceipt(id));
    }

    @PostMapping("/refund")
    public ResponseEntity<PaymentResponse> refundPayment(@Valid @RequestBody @NonNull RefundPaymentRequest request) {
        return ResponseEntity.ok(paymentService.refundPayment(request));
    }

    @GetMapping
    public ResponseEntity<Page<PaymentResponse>> filterPayments(
            @ModelAttribute PaymentFilterRequest filter,
            Pageable pageable) {
        return ResponseEntity.ok(paymentService.filterPayments(filter, pageable));
    }
}
