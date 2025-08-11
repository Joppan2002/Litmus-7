package com.litmus7.employeemanager.util;

import com.litmus7.employeemanager.dto.Employee;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtil {

    public static boolean isValidId(int id) {
        return id > 0;
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidMobileNumber(String mobile) {
        return mobile.matches("\\d{10}"); // 10 digits only
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidJoiningDate(String date) {
        try {
            LocalDate joiningDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return !joiningDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false");
    }

    
    public static String validateEmployee(Employee emp) {
        if (!isValidId(emp.getId())) {
            return "Invalid ID: Must be a positive integer.";
        }
        if (!isNotEmpty(emp.getFirstName())) {
            return "First Name cannot be empty.";
        }
        if (!isNotEmpty(emp.getLastName())) {
            return "Last Name cannot be empty.";
        }
        if (!isValidMobileNumber(emp.getMobileNumber())) {
            return "Invalid Mobile Number: Must be a 10-digit number.";
        }
        if (!isValidEmail(emp.getEmailID())) {
            return "Invalid Email Address.";
        }
        if (!isValidJoiningDate(emp.getJoiningDate())) {
            return "Invalid Joining Date: Must be in YYYY-MM-DD and not a future date.";
        }
        if (!isValidStatus(emp.getActiveStatus())) {
            return "Invalid Active Status: Must be true or false.";
        }

        return "valid";
    }


	

	
}
