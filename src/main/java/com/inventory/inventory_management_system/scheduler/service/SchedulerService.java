package com.inventory.inventory_management_system.scheduler.service;

import com.inventory.inventory_management_system.scheduler.job.BackupDatabaseJob;
import com.inventory.inventory_management_system.scheduler.job.ExpiredProductsJob;
import com.inventory.inventory_management_system.scheduler.job.LowStockJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {

    private final BackupDatabaseJob backupDatabaseJob;
    private final ExpiredProductsJob expiredProductsJob;
    private final LowStockJob lowStockJob;

    public void triggerManualBackup() {
        log.info("Manual trigger for database backup requested.");
        backupDatabaseJob.executeBackup();
    }

    public void triggerManualLowStockCheck() {
        log.info("Manual trigger for low stock check requested.");
        lowStockJob.checkLowStock();
    }

    public void triggerManualExpiredProductsCheck() {
        log.info("Manual trigger for expired products check requested.");
        expiredProductsJob.checkExpiredProducts();
    }
}
