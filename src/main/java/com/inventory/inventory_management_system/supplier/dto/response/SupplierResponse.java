package com.inventory.inventory_management_system.supplier.dto.response;

import lombok.Data;

@Data
public class SupplierResponse {

    private Long id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private Boolean active;
}