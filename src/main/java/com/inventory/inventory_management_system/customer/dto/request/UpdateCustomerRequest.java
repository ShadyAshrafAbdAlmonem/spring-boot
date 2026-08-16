package com.inventory.inventory_management_system.customer.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateCustomerRequest {

    private String firstName;
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private Boolean active;
}
