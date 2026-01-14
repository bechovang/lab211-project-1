/*
 * Inputter class for keyboard input with validation
 */
package com.feast.tools;

import java.util.Scanner;

/**
 * Utility class for handling user input with validation
 */
public class Inputter {

    private Scanner scanner;

    /**
     * Constructor initializes the Scanner
     */
    public Inputter() {
        scanner = new Scanner(System.in);
    }

    /**
     * Constructor with shared Scanner
     *
     * @param scanner the shared Scanner object
     */
    public Inputter(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Gets a non-empty string from user input
     *
     * @param mess the message to display to the user
     * @return a non-empty string input by the user
     */
    public String getString(String mess) {
        String result;
        while (true) {
            System.out.print(mess);
            result = scanner.nextLine().trim();
            if (!result.isEmpty()) {
                return result;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Gets an integer from user input
     *
     * @param mess the message to display to the user
     * @return a positive integer input by the user
     */
    public int getInt(String mess) {
        while (true) {
            System.out.print(mess);
            String input = scanner.nextLine().trim();
            if (Acceptable.isValid(input, Acceptable.INTEGER_VALID)) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid input. Please enter a positive integer.");
        }
    }

    /**
     * Gets a double from user input
     *
     * @param mess the message to display to the user
     * @return a positive double input by the user
     */
    public double getDouble(String mess) {
        while (true) {
            System.out.print(mess);
            String input = scanner.nextLine().trim();
            if (Acceptable.isValid(input, Acceptable.DOUBLE_VALID)) {
                return Double.parseDouble(input);
            }
            System.out.println("Invalid input. Please enter a positive number.");
        }
    }

    /**
     * Gets a string input with validation using regex pattern
     *
     * @param mess    the message to display to the user
     * @param pattern the regex pattern to validate against
     * @return a valid string input by the user
     */
    public String getValidString(String mess, String pattern) {
        while (true) {
            String input = getString(mess);
            if (Acceptable.isValid(input, pattern)) {
                return input;
            }
            System.out.println("Invalid input format. Please try again.");
        }
    }

    /**
     * Closes the scanner
     */
    public void close() {
        scanner.close();
    }
}
