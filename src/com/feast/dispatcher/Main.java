/*
 * Main class for Traditional Feast Order Management System
 */
package com.feast.dispatcher;

import com.feast.business.*;
import com.feast.model.*;
import com.feast.tools.Acceptable;
import com.feast.tools.Inputter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Main class controlling the feast order management system
 */
public class Main {

    private static Customers customers;
    private static SetMenus setMenus;
    private static Orders orders;
    private static Inputter inputter;
    private static Scanner scanner;
    private static final String CUSTOMER_FILE = "data/customers.dat";
    private static final String ORDER_FILE = "data/orders.dat";

    /**
     * Main entry point
     */
    public static void main(String[] args) {
        // Initialize shared scanner
        scanner = new Scanner(System.in);

        // Initialize inputter with shared scanner
        inputter = new Inputter(scanner);

        // Initialize business objects
        customers = new Customers(CUSTOMER_FILE);
        setMenus = new SetMenus("data/FeastMenu.csv");
        orders = new Orders(ORDER_FILE);

        // Load data
        System.out.println("=== Traditional Feast Order Management System ===");
        setMenus.readFromFile();
        customers.readFromFile();
        orders.readFromFile();

        // Run menu
        runMenu();
    }

    /**
     * Runs the main menu loop
     */
    private static void runMenu() {
        int choice;

        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                processChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                choice = 0;
            }
        } while (choice != 0);
    }

    /**
     * Displays the main menu
     */
    private static void displayMenu() {
        System.out.println("\n=================== MENU ===================");
        System.out.println("1.  Add new customer");
        System.out.println("2.  Update customer information");
        System.out.println("3.  Search for customer information by name");
        System.out.println("4.  Add new order");
        System.out.println("5.  Update order information");
        System.out.println("6.  Search for order information by order code");
        System.out.println("7.  Display menu list");
        System.out.println("8.  Display Customer or Order lists");
        System.out.println("9.  Save customers and orders to files");
        System.out.println("0.  Exit");
        System.out.println("============================================");
    }

    /**
     * Processes the user's menu choice
     *
     * @param choice the user's choice
     */
    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                addNewCustomer();
                break;
            case 2:
                updateCustomer();
                break;
            case 3:
                searchCustomerByName();
                break;
            case 4:
                addNewOrder();
                break;
            case 5:
                updateOrder();
                break;
            case 6:
                searchOrderByCode();
                break;
            case 7:
                setMenus.showMenuList();
                break;
            case 8:
                displayLists();
                break;
            case 9:
                saveAllData();
                break;
            case 0:
                System.out.println("Exiting program. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    /**
     * Adds a new customer
     */
    private static void addNewCustomer() {
        System.out.println("\n--- Add New Customer ---");
        String id = inputter.getValidString("Customer ID (CGKxxxx): ", Acceptable.CUS_ID_VALID);
        String name = inputter.getValidString("Customer Name (2-25 chars): ", Acceptable.NAME_VALID);
        String phone = inputter.getValidString("Phone (10 digits): ", Acceptable.PHONE_VALID);
        String email = inputter.getValidString("Email: ", Acceptable.EMAIL_VALID);

        Customer customer = new Customer(id, name, phone, email);
        customers.addNew(customer);
    }

    /**
     * Updates customer information
     */
    private static void updateCustomer() {
        System.out.println("\n--- Update Customer Information ---");
        String id = inputter.getString("Enter Customer ID to update: ");
        Customer existing = customers.searchById(id);

        if (existing == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.println("Current customer info:");
        System.out.println(Customer.getHeader());
        System.out.println(existing);
        System.out.println(Customer.getFooter());

        String name = inputter.getValidString("New Customer Name: ", Acceptable.NAME_VALID);
        String phone = inputter.getValidString("New Phone: ", Acceptable.PHONE_VALID);
        String email = inputter.getValidString("New Email: ", Acceptable.EMAIL_VALID);

        Customer updated = new Customer(id, name, phone, email);
        customers.update(updated);
    }

    /**
     * Searches for customer by name
     */
    private static void searchCustomerByName() {
        System.out.println("\n--- Search Customer by Name ---");
        String name = inputter.getString("Enter customer name (or part of name): ");
        List<Customer> result = customers.filterByName(name);
        customers.showAll(result);
    }

    /**
     * Adds a new order
     */
    private static void addNewOrder() {
        System.out.println("\n--- Place New Order ---");

        // Check if there are customers
        if (customers.isEmpty()) {
            System.out.println("No customers available. Please add a customer first.");
            return;
        }

        // Show customers for reference
        System.out.println("\nAvailable Customers:");
        customers.showAll();

        String customerId = inputter.getString("Enter Customer ID: ");
        Customer customer = customers.searchById(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        // Show menus for reference
        System.out.println("\nAvailable Menus:");
        setMenus.showMenuList();

        String menuId = inputter.getString("Enter Menu ID: ");
        SetMenu menu = setMenus.searchById(menuId);
        if (menu == null) {
            System.out.println("Menu not found!");
            return;
        }

        String province = inputter.getString("Enter Province/Location: ");
        int numOfTables = inputter.getInt("Enter Number of Tables: ");

        String dateStr = inputter.getString("Enter Event Date (dd/MM/yyyy): ");
        Date eventDate = parseDate(dateStr);
        if (eventDate == null) {
            System.out.println("Invalid date format!");
            return;
        }

        Order order = new Order();
        order.setOrderCodeWithGeneration();
        order.setCustomerId(customerId);
        order.setProvince(province);
        order.setMenuId(menuId);
        order.setNumOfTables(numOfTables);
        order.setEventDate(eventDate);

        orders.addNew(order);
    }

    /**
     * Updates order information
     */
    private static void updateOrder() {
        System.out.println("\n--- Update Order Information ---");
        String orderCode = inputter.getString("Enter Order Code to update: ");

        // Search by iterating through orders
        Order existing = null;
        for (Order o : orders) {
            if (o.getOrderCode().equals(orderCode)) {
                existing = o;
                break;
            }
        }

        if (existing == null) {
            System.out.println("Order not found!");
            return;
        }

        System.out.println("Current order info:");
        System.out.println(Order.getHeader());
        System.out.println(existing);
        System.out.println(Order.getFooter());

        String province = inputter.getString("New Province/Location: ");
        int numOfTables = inputter.getInt("New Number of Tables: ");

        String dateStr = inputter.getString("New Event Date (dd/MM/yyyy): ");
        Date eventDate = parseDate(dateStr);
        if (eventDate == null) {
            System.out.println("Invalid date format!");
            return;
        }

        Order updated = new Order(orderCode, existing.getCustomerId(),
                province, existing.getMenuId(), numOfTables, eventDate);
        orders.update(updated);
    }

    /**
     * Searches for order by code
     */
    private static void searchOrderByCode() {
        System.out.println("\n--- Search Order by Code ---");
        String orderCode = inputter.getString("Enter Order Code: ");
        Order order = orders.searchById(orderCode);

        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        System.out.println(Order.getHeader());
        System.out.println(order);
        System.out.println(Order.getFooter());
    }

    /**
     * Displays lists (customers or orders)
     */
    private static void displayLists() {
        System.out.println("\n--- Display Lists ---");
        System.out.println("1. Display all customers");
        System.out.println("2. Display all orders");
        String choice = inputter.getString("Enter your choice: ");

        switch (choice) {
            case "1":
                customers.showAll();
                break;
            case "2":
                orders.showAll();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * Saves all data to files
     */
    private static void saveAllData() {
        System.out.println("\n--- Save Data ---");
        customers.saveToFile();
        orders.saveToFile();
    }

    /**
     * Parses a date string in dd/MM/yyyy format
     *
     * @param dateStr the date string
     * @return the Date object, or null if invalid
     */
    private static Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
