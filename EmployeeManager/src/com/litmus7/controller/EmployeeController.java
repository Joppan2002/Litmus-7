package com.litmus7.employeemanager.controller;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.TextFileUtil;
import com.litmus7.employeemanager.util.ValidationUtil;
import com.litmus7.employeemanager.util.Response;
import com.litmus7.employeemanager.services.EmployeeService;
import com.litmus7.exceptions.EmployeeNotFoundException;
import com.litmus7.exceptions.EmployeeServiceException;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

    public String checkValidity(Employee emp, String outputFilePath) {
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

    EmployeeService service = new EmployeeService();

    public Boolean ValidationCheck(Employee emp) throws EmployeeServiceException {
        try {
            return ValidationUtil.validateEmployee(emp).equalsIgnoreCase("valid");
        } catch (Exception e) {
            throw new EmployeeServiceException("Error validating employee data", e);
        }
    }

    public boolean inserttoEmployee(Employee emp) throws EmployeeServiceException {
        if (emp == null) {
            throw new EmployeeServiceException("Cannot insert null Employee");
        }
        try {
            service.addEmployee(emp);
            return true;
        } catch (Exception e) {
            throw new EmployeeServiceException("Failed to insert employee", e);
        }
    }

    public List<Employee> fetchAllEmployees() throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            List<Employee> employees = service.RetrieveAll().getData();
            if (employees == null || employees.isEmpty()) {
                throw new EmployeeNotFoundException("No employees found");
            }
            return employees;
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeServiceException("Error fetching all employees", e);
        }
    }

    public Employee fetchWithId(int id) throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            Employee emp = service.RetrieveOne(id).getData();
            if (emp == null) {
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
            }
            return emp;
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeServiceException("Error fetching employee with ID: " + id, e);
        }
    }

    public boolean deletionInEmployee(int id) throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            boolean status = service.DeleteByID(id).getApplicationStatus();
            if (!status) {
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found for deletion");
            }
            return true;
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeServiceException("Error deleting employee with ID: " + id, e);
        }
    }

    public boolean nameUpdation(int ID, String newFirstName, String newLastName) throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            boolean status = service.UpdateName(ID, newFirstName, newLastName).getApplicationStatus();
            if (!status) {
                throw new EmployeeNotFoundException("Employee with ID " + ID + " not found for name update");
            }
            return true;
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeServiceException("Error updating employee name for ID: " + ID, e);
        }
    }
}
