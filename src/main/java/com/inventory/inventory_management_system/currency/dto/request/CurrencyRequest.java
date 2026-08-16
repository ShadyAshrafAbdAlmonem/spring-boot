package com.inventory.inventory_management_system.currency.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyRequest {

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters")
    private String code;

    @NotBlank(message = "Currency name is required")
    private String name;

    @NotBlank(message = "Currency symbol is required")
    private String symbol;

    @NotNull(message = "Exchange rate is required")
    @Positive(message = "Exchange rate must be positive")
    private BigDecimal exchangeRate;

    private Boolean isDefault;
    private Boolean active;
}
