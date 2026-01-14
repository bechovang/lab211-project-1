/*
 * Orders class for managing order list
 */
package com.feast.business;

import com.feast.model.Order;
import com.feast.tools.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Business class for managing Order objects
 * Uses HashSet internally to prevent duplicates
 */
public class Orders extends HashSet<Order> {

    private String pathFile;
    private boolean isSaved;

    /**
     * Default constructor
     */
    public Orders() {
        this.pathFile = "data/orders.dat";
        this.isSaved = true;
    }

    /**
     * Constructor with file path
     *
     * @param pathFile the path to the data file
     */
    public Orders(String pathFile) {
        this.pathFile = pathFile;
        this.isSaved = true;
    }

    /**
     * Checks if data has been saved
     *
     * @return true if saved, false otherwise
     */
    public boolean isSaved() {
        return isSaved;
    }

    /**
     * Checks if an order is duplicate (based on customerId, menuId, eventDate)
     *
     * @param order the order to check
     * @return true if duplicate, false otherwise
     */
    public boolean isDuplicate(Order order) {
        return this.contains(order);
    }

    /**
     * Adds a new order to the set
     *
     * @param order the order to add
     */
    public void addNew(Order order) {
        if (isDuplicate(order)) {
            System.out.println("Duplicate order! Same customer, menu, and date already exists.");
            return;
        }
        this.add(order);
        isSaved = false;
        System.out.println("Order placed successfully! Order Code: " + order.getOrderCode());
    }

    /**
     * Updates an existing order
     *
     * @param order the order with updated information
     */
    public void update(Order order) {
        // Remove and re-add since HashSet doesn't have direct update
        for (Order o : this) {
            if (o.getOrderCode().equals(order.getOrderCode())) {
                this.remove(o);
                this.add(order);
                isSaved = false;
                System.out.println("Order updated successfully!");
                return;
            }
        }
        System.out.println("Order Code not found!");
    }

    /**
     * Searches for an order by order code
     *
     * @param orderCode the order code to search for
     * @return the Order object if found, null otherwise
     */
    public Order searchById(String orderCode) {
        for (Order o : this) {
            if (o.getOrderCode().equals(orderCode)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Displays all orders
     */
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        System.out.println(Order.getHeader());
        for (Order o : this) {
            System.out.println(o);
        }
        System.out.println(Order.getFooter());
    }

    /**
     * Reads orders from file
     */
    public void readFromFile() {
        List<Order> list = FileUtils.readFromFile(pathFile);
        this.clear();
        this.addAll(list);
        isSaved = true;
        System.out.println("Loaded " + this.size() + " order(s) from file.");
    }

    /**
     * Saves orders to file
     */
    public void saveToFile() {
        FileUtils.saveToFile(new ArrayList<>(this), pathFile);
        isSaved = true;
        System.out.println("Saved " + this.size() + " order(s) to file.");
    }
}
