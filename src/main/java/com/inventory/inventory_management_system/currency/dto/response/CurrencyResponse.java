package com.inventory.inventory_management_system.currency.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyResponse {
    private Long id;
    private String code;
    private String name;
    private String symbol;
    private BigDecimal exchangeRate;
    private Boolean isDefault;
    private Boolean active;
}