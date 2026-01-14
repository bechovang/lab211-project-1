/*
 * SetMenu class representing a feast menu in the system
 */
package com.feast.model;

/**
 * Model class for SetMenu information
 */
public class SetMenu implements java.io.Serializable {

    private String menuId;
    private String menuName;
    private double price;
    private String ingredients;

    /**
     * Default constructor
     */
    public SetMenu() {
    }

    /**
     * Constructor with all fields
     *
     * @param menuId      the menu ID
     * @param menuName    the menu name
     * @param price       the price of the menu
     * @param ingredients the ingredients/description
     */
    public SetMenu(String menuId, String menuName, double price, String ingredients) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.ingredients = ingredients;
    }

    // Getters
    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public double getPrice() {
        return price;
    }

    public String getIngredients() {
        return ingredients;
    }

    // Setters
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Returns a string representation of the menu
     *
     * @return string with menu information
     */
    @Override
    public String toString() {
        return String.format("| %-8s | %-30s | %,.0f VND |",
                menuId, menuName, price);
    }

    /**
     * Prints header for menu table display
     *
     * @return the header string
     */
    public static String getHeader() {
        return "+----------+--------------------------------+----------------------+\n" +
                "| Menu ID  | Menu Name                      | Price                |\n" +
                "+----------+--------------------------------+----------------------+";
    }

    /**
     * Prints footer for menu table display
     *
     * @return the footer string
     */
    public static String getFooter() {
        return "+----------+--------------------------------+----------------------+";
    }
}
