package com.inventory.inventory_management_system.invoice.validator;

import com.inventory.inventory_management_system.invoice.dto.request.CreateInvoiceRequest;
import org.springframework.stereotype.Component;

@Component
public class InvoiceValidator {

    public void validateCreate(CreateInvoiceRequest request) {
        if (request.getDueDate().isBefore(request.getIssueDate())) {
            throw new IllegalArgumentException("Due date cannot be before issue date");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Invoice must contain at least one item");
        }
    }
}
