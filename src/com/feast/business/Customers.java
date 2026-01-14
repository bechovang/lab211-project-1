/*
 * Customers class for managing customer list
 */
package com.feast.business;

import com.feast.model.Customer;
import com.feast.tools.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Business class for managing Customer objects
 * Extends ArrayList to use as a collection
 */
public class Customers extends ArrayList<Customer> {

    private String pathFile;
    private boolean isSaved;

    /**
     * Default constructor
     */
    public Customers() {
        this.pathFile = "data/customers.dat";
        this.isSaved = true;
    }

    /**
     * Constructor with file path
     *
     * @param pathFile the path to the data file
     */
    public Customers(String pathFile) {
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
     * Adds a new customer to the list
     *
     * @param customer the customer to add
     */
    public void addNew(Customer customer) {
        // Check for duplicate ID
        for (Customer c : this) {
            if (c.getId().equals(customer.getId())) {
                System.out.println("Customer ID already exists!");
                return;
            }
        }
        this.add(customer);
        isSaved = false;
        System.out.println("Customer added successfully!");
    }

    /**
     * Updates an existing customer
     *
     * @param customer the customer with updated information
     */
    public void update(Customer customer) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(customer.getId())) {
                this.set(i, customer);
                isSaved = false;
                System.out.println("Customer updated successfully!");
                return;
            }
        }
        System.out.println("Customer ID not found!");
    }

    /**
     * Searches for a customer by ID
     *
     * @param id the customer ID to search for
     * @return the Customer object if found, null otherwise
     */
    public Customer searchById(String id) {
        for (Customer c : this) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Filters customers by name (partial match)
     *
     * @param name the name or part of name to search for
     * @return a list of matching customers
     */
    public List<Customer> filterByName(String name) {
        List<Customer> result = new ArrayList<>();
        String lowerName = name.toLowerCase();
        for (Customer c : this) {
            if (c.getName().toLowerCase().contains(lowerName)) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Displays all customers
     */
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.println(Customer.getHeader());
        for (Customer c : this) {
            System.out.println(c);
        }
        System.out.println(Customer.getFooter());
    }

    /**
     * Displays a list of customers
     *
     * @param list the list of customers to display
     */
    public void showAll(List<Customer> list) {
        if (list.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.println(Customer.getHeader());
        for (Customer c : list) {
            System.out.println(c);
        }
        System.out.println(Customer.getFooter());
    }

    /**
     * Reads customers from file
     */
    public void readFromFile() {
        List<Customer> list = FileUtils.readFromFile(pathFile);
        this.clear();
        this.addAll(list);
        isSaved = true;
        System.out.println("Loaded " + this.size() + " customer(s) from file.");
    }

    /**
     * Saves customers to file
     */
    public void saveToFile() {
        FileUtils.saveToFile(new ArrayList<>(this), pathFile);
        isSaved = true;
        System.out.println("Saved " + this.size() + " customer(s) to file.");
    }
}
