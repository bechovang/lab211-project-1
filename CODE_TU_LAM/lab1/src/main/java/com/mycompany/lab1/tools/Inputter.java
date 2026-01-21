/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.tools;

import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Inputter {
	// Pattern: Loop → print → Read → Validate → Return/Repeat
	// print : chi la in ra lai cai nguoi dung nhap
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static String getString(String mess) {
		while (true) {
			System.out.print(mess);
			String result = scanner.nextLine().trim();
			if (!result.isEmpty()) return result;
			System.out.println("Cannot be empty!");
		}
    }
	
	//de keu co ham nay
	public static int getInt(String mess) {
    while (true) {
        String input = getString(mess);
        if (Acceptable.isValid(input, Acceptable.INTEGER_VALID))
            return Integer.parseInt(input);
        System.out.println("Invalid!");
		}
	}
	
	public static String getValidString(String mess, String pattern) {
		while (true) {
			String input = getString(mess);
			if (Acceptable.isValid(input, pattern)) return input;
			System.out.println("Invalid format!");
		}
	}

	

	
}
