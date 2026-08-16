package com.inventory.inventory_management_system.customer.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean active;
    private List<AddressResponse> addresses;

    @Data
    public static class AddressResponse {
        private Long id;
        private String street;
        private String city;
        private String state;
        private String country;
        private String postalCode;
        private Boolean isDefault;
    }
}
