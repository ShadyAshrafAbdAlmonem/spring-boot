package com.inventory.inventory_management_system.address.dto.request;

import lombok.Data;

@Data
public class UpdateAddressRequest {

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
