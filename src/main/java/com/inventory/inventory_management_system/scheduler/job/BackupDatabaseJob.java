package com.inventory.inventory_management_system.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class BackupDatabaseJob {

    @Value("${app.backup.directory:./backups}")
    private String backupDirectory;

    @Value("${app.backup.db-name:inventory_db}")
    private String dbName;

    @Value("${spring.datasource.username:root}")
    private String dbUsername;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/inventory_db}")
    private String dbUrl;

    // Runs every day at 02:00 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void executeBackup() {
        log.info("Starting automated database backup process...");
        try {
            // Create backup directory if not exists
            Path backupDirPath = Paths.get(backupDirectory);
            if (!Files.exists(backupDirPath)) {
                Files.createDirectories(backupDirPath);
            }

            // Generate backup file name with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String backupFileName = dbName + "_backup_" + timestamp + ".sql";
            Path backupFilePath = backupDirPath.resolve(backupFileName);

            // Build mysqldump command
            // Assumes MySQL; adapt if using PostgreSQL
            String dbHost = extractHostFromUrl(dbUrl);
            String dbPort = extractPortFromUrl(dbUrl);
            String dbNameActual = extractDbNameFromUrl(dbUrl);

            ProcessBuilder pb = new ProcessBuilder(
                    "mysqldump",
                    "-h", dbHost,
                    "-P", dbPort,
                    "-u", dbUsername,
                    "-p" + dbPassword,
                    dbNameActual
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Capture output and write to file
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 java.io.FileOutputStream fos = new java.io.FileOutputStream(backupFilePath.toFile())) {

                String line;
                while ((line = reader.readLine()) != null) {
                    fos.write((line + System.lineSeparator()).getBytes());
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Database backup completed successfully. Backup file: {}", backupFilePath.toAbsolutePath());
            } else {
                log.error("mysqldump exited with code {}", exitCode);
            }

        } catch (Exception e) {
            log.error("Failed to back up database: {}", e.getMessage(), e);
        }
    }

    private String extractHostFromUrl(String url) {
        // jdbc:mysql://localhost:3306/inventory_db
        try {
            String[] parts = url.split("://");
            if (parts.length < 2) return "localhost";
            String hostPort = parts[1].split("/")[0];
            return hostPort.split(":")[0];
        } catch (Exception e) {
            return "localhost";
        }
    }

    private String extractPortFromUrl(String url) {
        try {
            String[] parts = url.split("://");
            if (parts.length < 2) return "3306";
            String hostPort = parts[1].split("/")[0];
            if (hostPort.contains(":")) {
                return hostPort.split(":")[1];
            }
            return "3306";
        } catch (Exception e) {
            return "3306";
        }
    }

    private String extractDbNameFromUrl(String url) {
        try {
            String[] parts = url.split("/");
            return parts[parts.length - 1];
        } catch (Exception e) {
            return dbName;
        }
    }
}