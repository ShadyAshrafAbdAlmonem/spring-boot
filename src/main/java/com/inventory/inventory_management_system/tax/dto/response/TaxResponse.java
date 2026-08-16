package com.inventory.inventory_management_system.tax.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TaxResponse {

    private Long id;
    private String name;
    private BigDecimal rate;
    private Boolean active;
}
