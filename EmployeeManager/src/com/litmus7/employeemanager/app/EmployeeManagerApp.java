package com.litmus7.employeemanager.app;

import com.litmus7.employeemanager.controller.*;
import com.litmus7.employeemanager.dto.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;



public class EmployeeManagerApp {
    public static void main(String args[]) throws IOException {
        Scanner in = new Scanner(System.in);
        int ch;

        System.out.println("Enter the phase to execute");
        System.out.println("1. Employee Data Ingestion & Console Display");
        System.out.println("2. Data Transformation & CSV Export");
        System.out.println("3. Interactive Data Entry & Appending");
        ch = in.nextInt();

        switch (ch) {
            case 1: {
                EmployeeController obj1 = new EmployeeController();
                String res = obj1.getEmployeeDataFromTextFile("C:\\Users\\ACER\\OneDrive\\Documents\\Litmus 7 assignments\\Java Assignment 1_Augustine Joel Joseph\\datafile.txt");
                System.out.println(res);
                break;
            }

            case 2: {
                EmployeeController obj2 = new EmployeeController();
                String res2 = obj2.writeEmployeeDatatoCSV("C:\\Users\\ACER\\OneDrive\\Documents\\Litmus 7 assignments\\Java Assignment 1_Augustine Joel Joseph\\datafile.txt", "outputFile.csv");
                System.out.println(res2);
                break;
            }

            case 3: {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Enter the ID");
                int id = Integer.parseInt(br.readLine());

                System.out.println("Enter the First Name");
                String firstName = br.readLine();

                System.out.println("Enter the Last Name");
                String lastName = br.readLine();

                System.out.println("Enter the Mobile Number");
                String mobileNumber = br.readLine();

                System.out.println("Enter the Email ID");
                String emailID = br.readLine();

                System.out.println("Enter the Joining Date (YYYY-MM-DD)");
                String joiningDate = br.readLine();

                System.out.println("Enter the Active Status (true/false)");
                String activeStatus = br.readLine();

                // Create Employee object
                Employee emp = new Employee(id, firstName, lastName, mobileNumber, emailID, joiningDate, activeStatus);

                EmployeeController ob3 = new EmployeeController();

                String outputFilePath = "outputFile.csv";
                String result = ob3.checkValidity(emp, outputFilePath);

                System.out.println(result);
                break;
            }

            default:
                System.out.println("Invalid Choice");
        }
        
        
        //second part
        
        System.out.println("1.Add to employee table"
        		+ "\n2.Retrieve all employee details"
        		+ "\n3.Retrieve employee with specific ID"
        		+ "\n4.Delete from employee table"
        		+ "\n5.Update name of the employee"
        		+ "\nEnter your choice");
        
        int ch2=in.nextInt();
        
        switch(ch2)
        {
        case 1:
        	System.out.println("Enter the ID");
        	int id=in.nextInt();
        	System.out.println("Enter the first name");
        	String firstName=in.next();
        	System.out.println("Enter the last name");
        	String lastName=in.next();
        	System.out.println("Enter the mobile number");
        	String mobileNumber=in.next();
        	System.out.println("Enter the email ID");
        	String emailId=in.next();
        	System.out.println("Enter the date in format(YYYY-MM-DD)");
        	String joiningDate=in.next();
        	System.out.println("Enter the active status");
        	String activeStatus=in.next();
        	
        	Employee employee = new Employee(id, firstName, lastName, mobileNumber, emailId, joiningDate, activeStatus);
        	
        	EmployeeController obj=new EmployeeController();
        	
        	if(obj.ValidationCheck(employee))
        	{
        		if(obj.inserttoEmployee(employee))
        		{
        			System.out.println("Inserted Successfully");
        		}
        		else
        		{
        			System.out.println("Insertion Failed");
        		}
        	}
        	else
        	{
        		System.out.println("Enter Valid Data");
        	}
        }

        in.close();
    }
}
