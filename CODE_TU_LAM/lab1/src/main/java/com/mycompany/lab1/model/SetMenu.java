/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.model;


public class SetMenu {
	private String menuId;
    private String menuName;
    private double price;
    private String ingredients;

	public SetMenu() {
	}

	public SetMenu(String menuId, String menuName, double price, String ingredients) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.price = price;
		this.ingredients = ingredients;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	
	@Override
    public String toString() {
        return String.format("| %-10s | %-30s | %15.0f Vnd |", 
                menuId, menuName, price); 
    }
	// ko in ingredients, lam 1 ham rieng, o phan sau
	
	
	
	
	
}
