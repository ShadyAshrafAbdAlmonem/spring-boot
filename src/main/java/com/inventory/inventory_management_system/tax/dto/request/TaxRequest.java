package com.inventory.inventory_management_system.tax.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxRequest {

    @NotBlank(message = "Tax name is required")
    private String name;

    @NotNull(message = "Tax rate is required")
    private BigDecimal rate;

    private Boolean active;
}
