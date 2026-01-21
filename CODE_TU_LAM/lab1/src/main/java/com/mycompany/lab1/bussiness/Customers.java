/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.bussiness;

import com.mycompany.lab1.model.Customer;
import com.mycompany.lab1.tools.FileUtils;
import java.util.ArrayList;
import java.util.List;


public class Customers extends ArrayList<Customer> implements Workable<Customer>{
	private String pathFile;
	private boolean isSaved;
	
	public Customers(){
		this.pathFile = "data/customers.dat";
		this.isSaved = true;  // ban dau chua co gi de luu
	}
	
	@Override
	public void addNew(Customer customer){
		for (Customer c : this){
			if (c.getId().equalsIgnoreCase(customer.getId())){ 
				System.out.println("ma KH nay da ton tai");
                return;
			}
		}
		this.add(customer);
        isSaved = false; //Co du lieu moi, chua duoc luu
        System.out.println("Them KH thanh cong!");
	}
	
	@Override
	public void update(Customer customer){
		for (int i=0;i<this.size();i++){
			Customer k = this.get(i);
			if (k.getId().equalsIgnoreCase(customer.getId())){
				this.set(i, customer); // i la index
				isSaved = false;
				System.out.println("Cap nhat KH thanh cong!");
				return;
			}
		}
		System.out.println("Loi: khong tim thay KH");
	}
	
	@Override
	public Customer searchById (String id){
		for (Customer c : this){
			if (c.getId().equalsIgnoreCase(id)){
				return c;
			}
		}
		return null;
	}
	
	
	public List<Customer> filterByName(String name){
		List<Customer> l = new ArrayList<>();
		for (Customer c : this){
			if (c.getName().toLowerCase().contains(name.toLowerCase())){
				l.add(c);
			}
		}
		return l;
	}
	

	
	// dung cho Customer, vd: listKH.showAll();
	@Override
	public void showAll(){
		if (this.isEmpty()){
			System.out.println("Danh sach trong!");
			return;
		}
		
		for (Customer c : this){
			System.out.println(c);
		}
	}
	
	// dung cho 1 danh sach bat ky
	public void showAll(List<Customer> list){
		if (list.isEmpty()){
			System.out.println("Danh sach trong!");
			return;
		}
		
		for (Customer c : list){
			System.out.println(c);
		}
	}
	
	public void readFromFile() {
		
        List<Customer> data =  FileUtils.readFromFile(pathFile);
        
        if (data != null) {
            this.clear();
            this.addAll(data);
            this.isSaved = true;
            System.out.println("Nap du lieu thanh cong!");
        }
    }
	
	public void saveToFile(){
		// chuyen 'this' (obj Customoers) thanh Arraylist de luu
		ArrayList<Customer> list = new ArrayList<>(this);
		// goi saveToFile de luu
		FileUtils.saveToFile(list, pathFile);
		
		this.isSaved = true;
		System.out.println("Da luu file thanh cong!");
	
	}
	
	
	
	
	
	
	
	
	
}
