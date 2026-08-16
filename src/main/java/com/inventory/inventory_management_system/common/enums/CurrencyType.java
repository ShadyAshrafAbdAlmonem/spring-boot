package com.inventory.inventory_management_system.common.enums;

/**
 * Supported currency types for the inventory management system.
 */
public enum CurrencyType {
    
    USD("US Dollar", "$", "USD"),
    EUR("Euro", "€", "EUR"),
    GBP("British Pound", "£", "GBP"),
    EGP("Egyptian Pound", "E£", "EGP"),
    SAR("Saudi Riyal", "﷼", "SAR"),
    AED("UAE Dirham", "د.إ", "AED"),
    KWD("Kuwaiti Dinar", "د.ك", "KWD"),
    QAR("Qatari Riyal", "﷼", "QAR"),
    BHD("Bahraini Dinar", "BD", "BHD"),
    OMR("Omani Rial", "﷼", "OMR"),
    JOD("Jordanian Dinar", "JD", "JOD"),
    LBP("Lebanese Pound", "L£", "LBP"),
    MAD("Moroccan Dirham", "MAD", "MAD"),
    TRY("Turkish Lira", "₺", "TRY"),
    CNY("Chinese Yuan", "¥", "CNY"),
    JPY("Japanese Yen", "¥", "JPY"),
    INR("Indian Rupee", "₹", "INR"),
    CAD("Canadian Dollar", "C$", "CAD"),
    AUD("Australian Dollar", "A$", "AUD");

    private final String displayName;
    private final String symbol;
    private final String code;

    CurrencyType(String displayName, String symbol, String code) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCode() {
        return code;
    }

    /**
     * Find currency by code
     */
    public static CurrencyType fromCode(String code) {
        for (CurrencyType currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Invalid currency code: " + code);
    }

    /**
     * Check if the given code is a valid currency
     */
    public static boolean isValidCode(String code) {
        for (CurrencyType currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }
}
