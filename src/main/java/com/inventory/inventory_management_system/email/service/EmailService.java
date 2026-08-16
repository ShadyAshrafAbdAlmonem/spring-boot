package com.inventory.inventory_management_system.email.service;

import com.inventory.inventory_management_system.email.dto.EmailRequest;
import com.inventory.inventory_management_system.email.dto.EmailResponse;
import org.springframework.lang.NonNull;

public interface EmailService {
    EmailResponse sendEmail(@NonNull EmailRequest request); // Already has @NonNull
}
