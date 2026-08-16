package com.inventory.inventory_management_system.invoice.controller;

import com.inventory.inventory_management_system.invoice.dto.request.CreateInvoiceRequest;
import com.inventory.inventory_management_system.invoice.dto.request.InvoiceFilterRequest;
import com.inventory.inventory_management_system.invoice.dto.response.InvoiceDetailsResponse;
import com.inventory.inventory_management_system.invoice.dto.response.InvoiceResponse;
import com.inventory.inventory_management_system.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceDetailsResponse> createInvoice(@Valid @RequestBody @NonNull CreateInvoiceRequest request) {
        return new ResponseEntity<>(invoiceService.createInvoice(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetailsResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<InvoiceDetailsResponse> getByNumber(@PathVariable String invoiceNumber) {
        return ResponseEntity.ok(invoiceService.getInvoiceByNumber(invoiceNumber));
    }

    @GetMapping
    public ResponseEntity<Page<InvoiceResponse>> filterInvoices(
            @ModelAttribute InvoiceFilterRequest filter,
            Pageable pageable) {
        return ResponseEntity.ok(invoiceService.filterInvoices(filter, pageable));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelInvoice(@PathVariable @NonNull Long id) {
        invoiceService.cancelInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
