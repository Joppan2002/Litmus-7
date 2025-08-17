package com.litmus7.employeemanager.controller;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exceptions.EmployeeNotFoundException;
import com.litmus7.employeemanager.exceptions.EmployeeServiceException;
import com.litmus7.employeemanager.util.TextFileUtil;
import com.litmus7.employeemanager.util.ValidationUtil;
import com.litmus7.employeemanager.util.Response;
import com.litmus7.employeemanager.services.EmployeeService;
import com.litmus7.employeemanager.constants.errorCode;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EmployeeController {

    public String getEmployeeDataFromTextFile(String inputFilePath) throws IOException {
        String res = "ID\tFirst_Name\tLast_Name\tMobile Number\tEmail\tJoining Date\tActive Status\n";
        res += TextFileUtil.readFromTextFile(inputFilePath);
        res = res.replace(',', '\t');

        Response<String, Boolean, String> ob = new Response<>(res, false, res);
        return ob.getData();
    }

    public String writeEmployeeDatatoCSV(String inputFilePath, String outputFilePath) throws IOException {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(outputFilePath, true));
            pw.write("ID,First Name,Last Name,Mobile Number,Email Address,Joining Date,Active Status\n");
            pw.write(TextFileUtil.readFromTextFile(inputFilePath));
            pw.close();
        } catch (FileNotFoundException e) {
            Response<String, Boolean, String> obj2 = new Response<>(null, false, "File Not Found");
            return obj2.getMessage();
        }

        Response<String, Boolean, String> obj2 = new Response<>(null, true, "Successfully Written to CSV File");
        return obj2.getMessage();
    }

    public String checkValidity(Employee emp, String outputFilePath) 
    {
        String validationStatus = ValidationUtil.validateEmployee(emp);
        

        if (validationStatus.equals("valid")) {
            String line = emp.getId() + "," +
                          emp.getFirstName() + "," +
                          emp.getLastName() + "," +
                          emp.getMobileNumber() + "," +
                          emp.getEmailID() + "," +
                          emp.getJoiningDate() + "," +
                          emp.getActiveStatus() + "\n";

            try {
                PrintWriter pw = new PrintWriter(new FileWriter(outputFilePath, true));
                pw.write(line);
                pw.close();

                Response<String, Boolean, String> obj3 = new Response<>(line, true, "Successfully Written " + line + " to CSV");
                return obj3.getMessage();

            } catch (Exception e) {
                Response<String, Boolean, String> obj3 = new Response<>("Cannot Write", false, "Cannot Write");
                return obj3.getMessage();
            }

        } else {
            Response<String, Boolean, String> obj3 = new Response<>("Invalid", false, validationStatus);
            return obj3.getMessage();
        }
    }
    
    
 // second part

    EmployeeService employeeservice = new EmployeeService();

    public Boolean ValidationCheck(Employee emp)
    {
        return ValidationUtil.validateEmployee(emp).equalsIgnoreCase("valid");
    }

    public boolean inserttoEmployee(Employee emp) throws EmployeeServiceException {
        if (emp == null) {
        	
            throw new EmployeeServiceException("ErrorCode.EC102",errorCode.EC102, null);
        }
        try {
            employeeservice.addEmployee(emp);
            return true;
        } catch (Exception e) {
            throw new EmployeeServiceException("ErrorCode.EC103",errorCode.EC103,null);
        }
    }

    public List<Employee> fetchAllEmployees() throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            List<Employee> employees = employeeservice.RetrieveAll().getData();
            if (employees == null || employees.isEmpty()) {
                throw new EmployeeNotFoundException("ErrorCode.EC104",errorCode.EC104);
            }
            return employees;
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeServiceException("ErrorCode.EC105",errorCode.EC105,null);
        }
    }

    public Employee fetchWithId(int id) throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            Employee emp = employeeservice.RetrieveOne(id).getData();
            if (emp == null) {
                throw new EmployeeNotFoundException("ErrorCode.EC106",errorCode.EC106);
            }
            return emp;
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeServiceException("ErrorCode.EC107",errorCode.EC107,null);
        }
    }

    public boolean deletionInEmployee(int id) throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            boolean status = employeeservice.DeleteByID(id).getApplicationStatus();
            if (!status) {
                throw new EmployeeNotFoundException("ErrorCode.EC108",errorCode.EC108);
            }
            return true;
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeServiceException("ErrorCode.EC109",errorCode.EC109,null);
        }
    }

    public boolean nameUpdation(int ID, String newFirstName, String newLastName) throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            boolean status = employeeservice.UpdateName(ID, newFirstName, newLastName).getApplicationStatus();
            if (!status) {
                throw new EmployeeNotFoundException("ErrorCode.EC110",errorCode.EC110);
            }
            return true;
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeServiceException("ErrorCode.EC111",errorCode.EC111,null);
        }
    }
}
