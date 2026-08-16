package com.inventory.inventory_management_system.email.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailBuilder {

    private final Map<String, Object> variables = new HashMap<>();

    public EmailBuilder with(String key, Object value) {
        this.variables.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return this.variables;
    }
}
