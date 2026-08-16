package com.inventory.inventory_management_system.warehouse.dto.response;

import lombok.Data;

@Data
public class WarehouseResponse {
    private Long id;
    private String name;
    private String code;
    private String location;
    private String address;
    private String managerName;
    private String phone;
    private Boolean active;
}
