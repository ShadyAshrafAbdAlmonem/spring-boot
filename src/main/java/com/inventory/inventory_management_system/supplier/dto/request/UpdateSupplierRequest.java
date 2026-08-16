package com.inventory.inventory_management_system.supplier.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateSupplierRequest {

    private String name;
    private String contactPerson;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private String address;
    private Boolean active;
}
