/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.bussiness;

import com.mycompany.lab1.model.Order;
import com.mycompany.lab1.model.SetMenu;
import com.mycompany.lab1.tools.FileUtils;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;


public class Orders extends ArrayList<Order> implements Workable<Order>{
	private String pathFile;
	private boolean isSaved;

	public Orders() {
		this.pathFile = "data/orders.dat";
		this.isSaved = true;
		this.readFromFile(); // nap du lieu khi khoi tao luon
	}
	
	
	public void addNew(Order order, Customers listKH, FeastMenus listMenu){
		if (listKH.searchById(order.getCustomerId()) == null){
			System.out.println("Loi: id KH ko ton tai");
			return;
		}
		
		if (listMenu.getMenuById(order.getMenuId()) == null){
			System.out.println("Loi: id Menu ko ton tai");
			return;
		}
		
		SetMenu menu = listMenu.getMenuById(order.getMenuId());
		double total = order.getNumOfTables() * menu.getPrice();
		order.setTotalCost(total);
		
		order.generateCode(); // QUAN TRONG:
		this.add(order);
		isSaved = false;
		System.out.println("Dat tiec thanh cong!");
		
	}
	
	public void saveToFile(){
		ArrayList<Order> o= new ArrayList<>(this);
		FileUtils.saveToFile(o, pathFile);

		isSaved = true;
	}

	public void readFromFile(){
		List<Order> o = FileUtils.readFromFile(pathFile);
		if (o != null){
			this.clear();
			this.addAll(o);
		}
	}

	// Implement Workable<T> interface

	@Override
	public void addNew(Order x) {
		this.add(x);
		isSaved = false;
	}

	@Override
	public void update(Order x) {
		for (int i = 0; i < this.size(); i++) {
			Order o = this.get(i);
			if (o.getOrderCode().equalsIgnoreCase(x.getOrderCode())) {
				this.set(i, x);
				isSaved = false;
				System.out.println("Cap nhat Order thanh cong!");
				return;
			}
		}
		System.out.println("Loi: khong tim thay Order");
	}

	@Override
	public Order searchById(String id) {
		for (Order o : this) {
			if (o.getOrderCode().equalsIgnoreCase(id)) {
				return o;
			}
		}
		return null;
	}

	@Override
	public void showAll() {
		if (this.isEmpty()) {
			System.out.println("Danh sach Order trong!");
			return;
		}

		for (Order o : this) {
			System.out.println(o);
		}
	}

	// Kiem tra trung Order (customerId + menuId + eventDate)
	public boolean isDuplicate(Order order) {
		return this.contains(order);  // Goi equals() da override trong Order
	}


}
