/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.tools;

/**
 *
 * @author Admin
 */
public interface Acceptable {
	String CUS_ID_VALID = "^[CcGgKk]\\d{4}$";      // C/G/K + 4 digits
    String NAME_VALID = "^.{2,25}$";                // 2-25 chars
    String PHONE_VALID = "^0\\d{9}$";               // 0 + 9 digits
    String EMAIL_VALID = "^[\\w.-]+@[\\w.-]+[.][a-zA-Z]{2,}$";
    String MENU_ID_VALID = "^PW\\d{3}$";            // PW + 3 digits
	String INTEGER_VALID = "^[1-9]\\d*"; // So nguyen duong, khong bat dau bang 0

    static boolean isValid(String data, String pattern) {
        // keep data dont null
		if (data == null) 
			return false; 
		
		// check with pattern
		if (data.matches(pattern) == true)
			return true;
		else
			return false;
    }
	
	
}
