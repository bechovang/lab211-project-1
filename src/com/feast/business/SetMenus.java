/*
 * SetMenus class for managing set menu list
 */
package com.feast.business;

import com.feast.model.SetMenu;
import com.feast.tools.Acceptable;
import com.feast.tools.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Business class for managing SetMenu objects
 */
public class SetMenus extends ArrayList<SetMenu> {

    private String pathFile;

    /**
     * Default constructor
     */
    public SetMenus() {
        this.pathFile = "data/FeastMenu.csv";
    }

    /**
     * Constructor with file path
     *
     * @param pathFile the path to the CSV file
     */
    public SetMenus(String pathFile) {
        this.pathFile = pathFile;
    }

    /**
     * Validates menu ID format
     *
     * @param menuId the menu ID to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidMenuID(String menuId) {
        return Acceptable.isValid(menuId, Acceptable.MENU_ID_VALID);
    }

    /**
     * Converts a CSV line to SetMenu object
     *
     * @param text the CSV line
     * @return the SetMenu object
     */
    public SetMenu dataToObject(String text) {
        // Split by comma, but handle quoted ingredients
        String[] parts = text.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        if (parts.length < 4) {
            return null;
        }

        String menuId = parts[0].trim();
        String menuName = parts[1].trim();
        double price = Double.parseDouble(parts[2].trim());
        String ingredients = parts[3].trim();

        // Remove quotes if present
        if (ingredients.startsWith("\"") && ingredients.endsWith("\"")) {
            ingredients = ingredients.substring(1, ingredients.length() - 1);
        }

        return new SetMenu(menuId, menuName, price, ingredients);
    }

    /**
     * Reads menus from CSV file
     */
    public void readFromFile() {
        this.clear();

        List<String> lines = FileUtils.readTextFile(pathFile);

        // Skip header row
        boolean firstLine = true;
        for (String line : lines) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            // Remove BOM if present
            if (line.startsWith("\uFEFF")) {
                line = line.substring(1);
            }

            SetMenu menu = dataToObject(line);
            if (menu != null) {
                this.add(menu);
            }
        }

        System.out.println("Loaded " + this.size() + " menu(s) from file.");
    }

    /**
     * Displays all menus
     */
    public void showMenuList() {
        if (this.isEmpty()) {
            System.out.println("No menus found.");
            return;
        }
        System.out.println(SetMenu.getHeader());
        for (SetMenu menu : this) {
            System.out.println(menu);
        }
        System.out.println(SetMenu.getFooter());
    }

    /**
     * Searches for a menu by ID
     *
     * @param menuId the menu ID to search for
     * @return the SetMenu object if found, null otherwise
     */
    public SetMenu searchById(String menuId) {
        for (SetMenu menu : this) {
            if (menu.getMenuId().equals(menuId)) {
                return menu;
            }
        }
        return null;
    }
}
