/*
 * Order class representing a feast order in the system
 */
package com.feast.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Model class for Order information
 */
public class Order implements java.io.Serializable {

    private String orderCode;
    private String customerId;
    private String province;
    private String menuId;
    private int numOfTables;
    private Date eventDate;

    /**
     * Default constructor
     */
    public Order() {
        this.eventDate = new Date();
    }

    /**
     * Constructor with all fields
     *
     * @param orderCode  the order code
     * @param customerId the customer ID
     * @param province   the province/location
     * @param menuId     the menu ID
     * @param numOfTables the number of tables
     * @param eventDate  the event date
     */
    public Order(String orderCode, String customerId, String province,
                 String menuId, int numOfTables, Date eventDate) {
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.province = province;
        this.menuId = menuId;
        this.numOfTables = numOfTables;
        this.eventDate = eventDate;
    }

    /**
     * Generates a unique order code based on current timestamp
     *
     * @return the generated order code
     */
    private String generateOrderCode() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        return sdf.format(now);
    }

    /**
     * Sets the order code with auto-generation
     */
    public void setOrderCodeWithGeneration() {
        this.orderCode = generateOrderCode();
    }

    // Getters
    public String getOrderCode() {
        return orderCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProvince() {
        return province;
    }

    public String getMenuId() {
        return menuId;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public Date getEventDate() {
        return eventDate;
    }

    // Setters
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Returns a string representation of the order
     *
     * @return string with order information
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("| %-15s | %-12s | %-20s | %-8s | %-13s | %-12s |",
                orderCode, customerId, province, menuId, numOfTables,
                dateFormat.format(eventDate));
    }

    /**
     * Prints header for order table display
     *
     * @return the header string
     */
    public static String getHeader() {
        return "+-----------------+--------------+----------------------+----------+---------------+--------------+\n" +
                "| Order Code      | Customer ID  | Province             | Menu ID  | Num Of Tables | Event Date   |\n" +
                "+-----------------+--------------+----------------------+----------+---------------+--------------+";
    }

    /**
     * Prints footer for order table display
     *
     * @return the footer string
     */
    public static String getFooter() {
        return "+-----------------+--------------+----------------------+----------+---------------+--------------+";
    }

    /**
     * Checks equality based on customerId, menuId, and eventDate
     * Used for duplicate detection in HashSet
     *
     * @param o the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return Objects.equals(customerId, order.customerId) &&
                Objects.equals(menuId, order.menuId) &&
                Objects.equals(dateFormat.format(eventDate),
                        dateFormat.format(order.eventDate));
    }

    /**
     * Generates hashCode based on customerId, menuId, and eventDate
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return Objects.hash(customerId, menuId, dateFormat.format(eventDate));
    }
}
