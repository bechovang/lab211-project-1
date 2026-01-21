/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.tools;


import com.mycompany.lab1.model.SetMenu;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileUtils {
	//saveToFile : ham luu danh sach bat ky thanh file nhi phan 
	public static <T> void saveToFile (List<T> list, String filePath) {
		try (FileOutputStream fos =  new FileOutputStream(filePath);
				ObjectOutputStream oos = new ObjectOutputStream(fos)){
				for (T item : list){
					oos.writeObject(item);
				}
				System.out.println("Luu du lieu thanh cong!");
		}
		catch(IOException e){
			System.err.println("Loi khi luu file: " + e.getMessage());
		}
	}
	
	//readFromFile: ham doc/lay ra danh sach tu file nhi phan
	public static <T> List<T> readFromFile (String filePath) {
		List<T> list = new ArrayList<>();
		File file = new File(filePath);

		// Neu file chua ton tai, tra ve list rong (lan dau chay)
		if (!file.exists()) {
			return list;
		}

		try (FileInputStream fis = new FileInputStream(file);
			 ObjectInputStream ois = new ObjectInputStream(fis)){

			while (fis.available()>0){;
				T obj = (T) ois.readObject();
				list.add(obj);
			}


		}
		catch(IOException | ClassNotFoundException e){
			System.err.println("Error reading file: " + e.getMessage());
		}

		return list;

	}
	
	//	Hàm doc file CSV (readMenus)
	public static List<SetMenu> readMenus(String filePath) {
		List<SetMenu> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
			String line;
			while ((line = br.readLine()) != null){
				String[] parts = line.split(",");
				if (parts.length >= 4){
					String id = parts[0].trim();
					String name = parts[1].trim();
					double price = Double.parseDouble(parts[2].trim());
					String ingredients = parts[3].trim();

					list.add(new SetMenu(id, name, price, ingredients));
				}
			}
		}
		catch (IOException e){
			System.out.println("Loi doc file CSV: " + e.getMessage());
		}

		return list;
	}
	
	
	
	
}
