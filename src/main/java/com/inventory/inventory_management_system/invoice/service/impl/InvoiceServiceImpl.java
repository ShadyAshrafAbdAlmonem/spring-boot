package com.inventory.inventory_management_system.invoice.service.impl;

import com.inventory.inventory_management_system.invoice.dto.request.CreateInvoiceRequest;
import com.inventory.inventory_management_system.invoice.dto.request.InvoiceFilterRequest;
import com.inventory.inventory_management_system.invoice.dto.response.InvoiceDetailsResponse;
import com.inventory.inventory_management_system.invoice.dto.response.InvoiceResponse;
import com.inventory.inventory_management_system.invoice.entity.Invoice;
import com.inventory.inventory_management_system.invoice.entity.InvoiceItem;
import com.inventory.inventory_management_system.invoice.mapper.InvoiceMapper;
import com.inventory.inventory_management_system.invoice.repository.InvoiceRepository;
import com.inventory.inventory_management_system.invoice.service.InvoiceService;
import com.inventory.inventory_management_system.invoice.validator.InvoiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceValidator invoiceValidator;

    @Override
    public InvoiceDetailsResponse createInvoice(@NonNull CreateInvoiceRequest request) {
        invoiceValidator.validateCreate(request);

        Invoice invoice = invoiceMapper.toEntity(request);
        invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        invoice.setStatus("ISSUED");
        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setCreatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreateInvoiceRequest.InvoiceItemRequest itemDto : request.getItems()) {
            InvoiceItem item = invoiceMapper.toItemEntity(itemDto);
            BigDecimal itemTotal = itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            item.setTotalPrice(itemTotal);
            item.setInvoice(invoice);
            invoice.getItems().add(item);
            totalAmount = totalAmount.add(itemTotal);
        }

        invoice.setTotalAmount(totalAmount);
        return invoiceMapper.toDetailsResponse(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceDetailsResponse getInvoiceById(@NonNull Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        return invoiceMapper.toDetailsResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceDetailsResponse getInvoiceByNumber(@NonNull String invoiceNumber) {
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new RuntimeException("Invoice not found with number: " + invoiceNumber));
        return invoiceMapper.toDetailsResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvoiceResponse> filterInvoices(@NonNull InvoiceFilterRequest filter, @NonNull Pageable pageable) {
        if (filter.getCustomerId() != null) {
            return invoiceRepository.findByCustomerId(filter.getCustomerId(), pageable)
                    .map(invoiceMapper::toResponse);
        }
        return invoiceRepository.findAll(pageable).map(invoiceMapper::toResponse);
    }

    @Override
    public void cancelInvoice(@NonNull Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));

        if ("PAID".equals(invoice.getStatus())) {
            throw new IllegalStateException("Cannot cancel an invoice that is already paid");
        }

        invoice.setStatus("CANCELLED");
        invoiceRepository.save(invoice);
    }
}
