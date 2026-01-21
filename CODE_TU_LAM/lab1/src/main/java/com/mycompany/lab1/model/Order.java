/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class Order implements java.io.Serializable{
	private String orderCode;
    private String customerId;
    private String menuId;
    private int numOfTables;
    private Date eventDate;
	private double totalCost;

	public Order() {
	}

	public Order(String orderCode, String customerId, String menuId, int numOfTables, Date eventDate, double totalCost) {
		this.orderCode = orderCode;
		this.customerId = customerId;
		this.menuId = menuId;
		this.numOfTables = numOfTables;
		this.eventDate = eventDate;
		this.totalCost = totalCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public int getNumOfTables() {
		return numOfTables;
	}

	public void setNumOfTables(int numOfTables) {
		this.numOfTables = numOfTables;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	public void generateCode() {
		// lay thoi gian ngay bay gio
		 Date now = new Date(); 
		 // chon dinh dang tu nam den giay
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
		 // bien thoi gian thanh string
		 this.orderCode = sdf.format(now); 
	}

	@Override
	public boolean equals(Object o) {
		// valid
		// ko tu so sanh voi chinh minh
		if (this == o) return true;  
		// ko null hoac khac kieu
		if (o == null || getClass() != o.getClass()) return false; 

		Order order = (Order) o; //ep kieu, vi dau vao la object

		// check TRÙNG, dung ! - not
		if (!Objects.equals(this.customerId, order.customerId))
			return false;
		if (!Objects.equals(this.menuId, order.menuId)) 
			return false; 

		// check trung bang ngay thang nam
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		String date1 = sdf.format(this.eventDate);
		String date2 = sdf.format(order.eventDate);
		if (!Objects.equals(date1, date2)) 
			return false; 

		return true; 
	}

	
	// config hashcode
	@Override
	public int hashCode() {
		// check bang ngay-thang-nam
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		return Objects.hash(customerId, menuId, sdf.format(eventDate)); 
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ngayThangNam = sdf.format(eventDate);
		return String.format("| %-15s | %-10s | %-10s | %-10d | %-12s | %12.0f |", 
				orderCode, customerId, menuId, numOfTables, ngayThangNam, totalCost);
	}
	
	
	
}



