package com.inventory.inventory_management_system.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * Utility class for date and time operations.
 */
public class DateUtil {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Get current date and time
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Get current date
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * Get current time
     */
    public static LocalTime currentTime() {
        return LocalTime.now();
    }

    /**
     * Format date to string
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DEFAULT_DATE_FORMATTER) : null;
    }

    /**
     * Format date and time to string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_DATE_TIME_FORMATTER) : null;
    }

    /**
     * Format date and time to ISO format
     */
    public static String formatISO(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(ISO_FORMATTER) : null;
    }

    /**
     * Parse date string to LocalDate
     */
    public static LocalDate parseDate(String dateStr) {
        return dateStr != null ? LocalDate.parse(dateStr, DEFAULT_DATE_FORMATTER) : null;
    }

    /**
     * Parse date-time string to LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, DEFAULT_DATE_TIME_FORMATTER) : null;
    }

    /**
     * Parse ISO date-time string
     */
    public static LocalDateTime parseISO(String isoDateTime) {
        return isoDateTime != null ? LocalDateTime.parse(isoDateTime, ISO_FORMATTER) : null;
    }

    /**
     * Get start of day
     */
    public static LocalDateTime startOfDay(LocalDate date) {
        return date != null ? date.atStartOfDay() : null;
    }

    /**
     * Get end of day
     */
    public static LocalDateTime endOfDay(LocalDate date) {
        return date != null ? date.atTime(LocalTime.MAX) : null;
    }

    /**
     * Get start of month
     */
    public static LocalDateTime startOfMonth(LocalDate date) {
        return date != null ? date.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay() : null;
    }

    /**
     * Get end of month
     */
    public static LocalDateTime endOfMonth(LocalDate date) {
        return date != null ? date.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX) : null;
    }

    /**
     * Calculate days between two dates
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Calculate hours between two date-times
     */
    public static long hoursBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.HOURS.between(start, end);
    }

    /**
     * Calculate minutes between two date-times
     */
    public static long minutesBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.MINUTES.between(start, end);
    }

    /**
     * Add days to a date
     */
    public static LocalDate addDays(LocalDate date, long days) {
        return date != null ? date.plusDays(days) : null;
    }

    /**
     * Add hours to a date-time
     */
    public static LocalDateTime addHours(LocalDateTime dateTime, long hours) {
        return dateTime != null ? dateTime.plusHours(hours) : null;
    }

    /**
     * Subtract days from a date
     */
    public static LocalDate subtractDays(LocalDate date, long days) {
        return date != null ? date.minusDays(days) : null;
    }

    /**
     * Check if date is in the past
     */
    public static boolean isPast(LocalDate date) {
        return date != null && date.isBefore(today());
    }

    /**
     * Check if date is in the future
     */
    public static boolean isFuture(LocalDate date) {
        return date != null && date.isAfter(today());
    }

    /**
     * Check if date is today
     */
    public static boolean isToday(LocalDate date) {
        return date != null && date.equals(today());
    }

    /**
     * Convert LocalDateTime to Instant
     */
    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.atZone(ZoneId.systemDefault()).toInstant() : null;
    }

    /**
     * Convert Instant to LocalDateTime
     */
    public static LocalDateTime fromInstant(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneId.systemDefault()) : null;
    }

    /**
     * Get timezone offset
     */
    public static ZoneOffset getZoneOffset() {
        return ZoneOffset.systemDefault().getRules().getOffset(Instant.now());
    }

    /**
     * Get age from birth date
     */
    public static int getAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, today()).getYears();
    }

    /**
     * Check if a date is expired (before today)
     */
    public static boolean isExpired(LocalDate expiryDate) {
        return expiryDate != null && expiryDate.isBefore(today());
    }

    /**
     * Get duration until expiration
     */
    public static long getDaysUntilExpiry(LocalDate expiryDate) {
        return expiryDate != null ? ChronoUnit.DAYS.between(today(), expiryDate) : 0;
    }
}
