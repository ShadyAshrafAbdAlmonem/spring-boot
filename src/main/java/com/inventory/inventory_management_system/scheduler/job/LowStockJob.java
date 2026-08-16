package com.inventory.inventory_management_system.scheduler.job;

import com.inventory.inventory_management_system.notification.dto.request.SendNotificationRequest;
import com.inventory.inventory_management_system.notification.service.EmailNotificationService;
import com.inventory.inventory_management_system.product.entity.Product;
import com.inventory.inventory_management_system.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class LowStockJob {

    private final ProductRepository productRepository;
    private final EmailNotificationService emailNotificationService;

    @Value("${notification.manager.email:manager@default.com}")
    private String managerEmail;

    // Runs every 4 hours
    @Scheduled(cron = "0 0 */4 * * ?")
    @Transactional
    public void checkLowStock() {
        log.info("Scanning inventory for low stock alerts...");
        
        try {
            // Query products where quantity <= minQuantity and product is active
            List<Product> lowStockProducts = productRepository.findLowStockProducts();
            
            if (lowStockProducts.isEmpty()) {
                log.info("No low stock products found.");
                return;
            }

            log.info("Found {} products with low stock", lowStockProducts.size());

            // Trigger restock notifications
            sendLowStockNotification(lowStockProducts);
            
            log.info("Low stock check completed. {} products need restocking.", lowStockProducts.size());

        } catch (Exception e) {
            log.error("Error checking low stock products: {}", e.getMessage(), e);
        }
    }

    private void sendLowStockNotification(List<Product> lowStockProducts) {
        try {
            StringBuilder message = new StringBuilder();
            message.append("The following products are running low on stock and need restocking:\n\n");
            
            for (Product product : lowStockProducts) {
                message.append("- ").append(product.getName())
                       .append(" (SKU: ").append(product.getSku())
                       .append(") - Current Stock: ").append(product.getQuantity())
                       .append(", Minimum Required: ").append(product.getMinQuantity())
                       .append("\n");
            }

            SendNotificationRequest request = SendNotificationRequest.builder()
                    .recipientTarget(managerEmail)
                    .title("Low Stock Alert - Restock Required")
                    .message(message.toString())
                    .build();

            boolean sent = emailNotificationService.send(request);
            if (sent) {
                log.info("Low stock notification sent to inventory manager");
            } else {
                log.error("Failed to send low stock notification to inventory manager");
            }

        } catch (Exception e) {
            log.error("Error sending low stock notification: {}", e.getMessage(), e);
        }
    }
}