package com.inventory.inventory_management_system.invoice.service;

import com.inventory.inventory_management_system.invoice.dto.request.CreateInvoiceRequest;
import com.inventory.inventory_management_system.invoice.dto.request.InvoiceFilterRequest;
import com.inventory.inventory_management_system.invoice.dto.response.InvoiceDetailsResponse;
import com.inventory.inventory_management_system.invoice.dto.response.InvoiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface InvoiceService {
    InvoiceDetailsResponse createInvoice(@NonNull CreateInvoiceRequest request);
    InvoiceDetailsResponse getInvoiceById(@NonNull Long id);
    InvoiceDetailsResponse getInvoiceByNumber(@NonNull String invoiceNumber);
    Page<InvoiceResponse> filterInvoices(@NonNull InvoiceFilterRequest filter, @NonNull Pageable pageable);
    void cancelInvoice(@NonNull Long id);
}
