/*
 * Customer class representing a customer in the feast order system
 */
package com.feast.model;

/**
 * Model class for Customer information
 */
public class Customer implements java.io.Serializable {

    private String id;
    private String name;
    private String phone;
    private String email;

    /**
     * Default constructor
     */
    public Customer() {
    }

    /**
     * Constructor with all fields
     *
     * @param id    the customer ID
     * @param name  the customer name
     * @param phone the customer phone number
     * @param email the customer email
     */
    public Customer(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the customer
     *
     * @return string with customer information
     */
    @Override
    public String toString() {
        return String.format("| %-10s | %-25s | %-15s | %-30s |",
                id, name, phone, email);
    }

    /**
     * Prints header for customer table display
     *
     * @return the header string
     */
    public static String getHeader() {
        return "+------------+---------------------------+-----------------+--------------------------------+\n" +
                "| Customer ID| Name                      | Phone           | Email                          |\n" +
                "+------------+---------------------------+-----------------+--------------------------------+";
    }

    /**
     * Prints footer for customer table display
     *
     * @return the footer string
     */
    public static String getFooter() {
        return "+------------+---------------------------+-----------------+--------------------------------+";
    }
}
