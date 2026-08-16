package com.inventory.inventory_management_system.payment.mapper;

import com.inventory.inventory_management_system.payment.dto.request.CreatePaymentRequest;
import com.inventory.inventory_management_system.payment.dto.response.PaymentReceiptResponse;
import com.inventory.inventory_management_system.payment.dto.response.PaymentResponse;
import com.inventory.inventory_management_system.payment.entity.Payment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    Payment toEntity(CreatePaymentRequest request);

    @Mapping(target = "paymentMethodName", source = "paymentMethod.name")
    PaymentResponse toResponse(Payment entity);

    @Mapping(target = "receiptNumber", expression = "java(\"RCT-\" + entity.getTransactionId())")
    @Mapping(target = "amountPaid", source = "amount")
    @Mapping(target = "paymentMethod", source = "paymentMethod.name")
    @Mapping(target = "timestamp", source = "paymentDate")
    PaymentReceiptResponse toReceiptResponse(Payment entity);
}
