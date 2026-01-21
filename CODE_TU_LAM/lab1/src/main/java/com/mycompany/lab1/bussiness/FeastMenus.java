/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.bussiness;

import com.mycompany.lab1.model.SetMenu;
import com.mycompany.lab1.tools.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FeastMenus extends ArrayList<SetMenu>{
	private String pathFile;

	public FeastMenus() {
		this.pathFile = "data/FeastMenu.csv";
		// Vua tao doi tuong, la nap du lieu luon
        this.loadData();
	}
	
	private void loadData(){
		List<SetMenu> list = FileUtils.readMenus(pathFile);
		
		if (list != null && !list.isEmpty()){
			this.clear();
			this.addAll(list);
			System.out.println("nap du lieu thanh cong!");
		}
		else{
			System.out.println("Loi: khong thay thuc don nao de nap!");
		}
		
	}
	
	
	public SetMenu getMenuById(String id){
		for (SetMenu menu : this){
			if (menu.getMenuId().equalsIgnoreCase(id))
				return menu;
		}
		return null;
	}
	
	public void showAll(){
		for (SetMenu menu : this){
			System.out.println(menu);
		}
	}
	
	
}
