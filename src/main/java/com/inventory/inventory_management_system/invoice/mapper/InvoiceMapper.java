package com.inventory.inventory_management_system.invoice.mapper;

import com.inventory.inventory_management_system.invoice.dto.request.CreateInvoiceRequest;
import com.inventory.inventory_management_system.invoice.dto.response.InvoiceDetailsResponse;
import com.inventory.inventory_management_system.invoice.dto.response.InvoiceResponse;
import com.inventory.inventory_management_system.invoice.entity.Invoice;
import com.inventory.inventory_management_system.invoice.entity.InvoiceItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoiceNumber", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "paidAmount", ignore = true)
    @Mapping(target = "status", ignore = true)
    Invoice toEntity(CreateInvoiceRequest request);

    InvoiceResponse toResponse(Invoice entity);

    InvoiceDetailsResponse toDetailsResponse(Invoice entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    InvoiceItem toItemEntity(CreateInvoiceRequest.InvoiceItemRequest itemRequest);

    InvoiceDetailsResponse.InvoiceItemResponse toItemResponse(InvoiceItem item);
}
