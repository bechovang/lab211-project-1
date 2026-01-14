/*
 * Acceptable interface for data validation
 * Provides regex patterns and static validation method
 */
package com.feast.tools;

/**
 * Interface for validating input data using regex patterns
 */
public interface Acceptable {

    // Regex patterns for validation
    String CUS_ID_VALID = "^[CcGgKk]\\d{4}$";           // Customer ID: starts with C/c/G/g/K/k followed by 4 digits
    String NAME_VALID = "^.{2,25}$";                     // Name: 2-25 characters
    String PHONE_VALID = "^0\\d{9}$";                    // Phone: starts with 0, followed by 9 digits
    String EMAIL_VALID = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";  // Email format
    String MENU_ID_VALID = "^PW\\d{3}$";                 // Menu ID: starts with PW followed by 3 digits
    String DOUBLE_VALID = "^[1-9]\\d*(\\.\\d+)?$";       // Positive double
    String INTEGER_VALID = "^[1-9]\\d*$";                // Positive integer

    /**
     * Validates data against a regex pattern
     *
     * @param data    the data to validate
     * @param pattern the regex pattern to match against
     * @return true if data matches the pattern, false otherwise
     */
    static boolean isValid(String data, String pattern) {
        if (data == null || pattern == null) {
            return false;
        }
        return data.matches(pattern);
    }
}
