package com.inventory.inventory_management_system.report.util;

import com.inventory.inventory_management_system.report.enums.ReportPeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateRangeUtil {

    public static LocalDateTime getStartDateTime(ReportPeriod period, LocalDate customStart) {
        if (period == ReportPeriod.CUSTOM && customStart != null) {
            return customStart.atStartOfDay();
        }
        LocalDate now = LocalDate.now();
        return switch (period) {
            case DAILY -> now.atStartOfDay();
            case WEEKLY -> now.minusWeeks(1).atStartOfDay();
            case MONTHLY -> now.minusMonths(1).atStartOfDay();
            case QUARTERLY -> now.minusMonths(3).atStartOfDay();
            case YEARLY -> now.minusYears(1).atStartOfDay();
            default -> now.atStartOfDay();
        };
    }

    public static LocalDateTime getEndDateTime(ReportPeriod period, LocalDate customEnd) {
        if (period == ReportPeriod.CUSTOM && customEnd != null) {
            return customEnd.atTime(LocalTime.MAX);
        }
        return LocalDateTime.now();
    }
}