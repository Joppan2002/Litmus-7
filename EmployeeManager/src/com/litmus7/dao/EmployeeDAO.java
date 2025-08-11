package com.litmus7.employeemanager.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.util.DatabaseConnectionUtil;
import com.litmus7.employeemanager.dto.Employee;

public class employeeDAO {

    public employeeDAO() {
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createEmployee(Employee emp) {
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String sql = "INSERT INTO employee(id,firstName,lastName,mobileNumber,emailId,joiningDate,activeStatus) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = sqlConn.prepareStatement(sql);

            pstmt.setInt(1, emp.getId());
            pstmt.setString(2, emp.getFirstName());
            pstmt.setString(3, emp.getLastName());
            pstmt.setString(4, emp.getMobileNumber());
            pstmt.setString(5, emp.getEmailID());
            pstmt.setDate(6, Date.valueOf(LocalDate.parse(emp.getJoiningDate())));
            pstmt.setBoolean(7, Boolean.parseBoolean(emp.getActiveStatus()));

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Employee> getAllEmployee() {
        List<Employee> employees = new ArrayList<>();

        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String sqlfetch = "SELECT id,firstName,lastName,mobileNumber,emailID,joiningDate,activeStatus FROM employee";
            Statement stmt = sqlConn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlfetch);

            while (rs.next()) {
                Employee emp =new Employee(rs.getInt("ID"),rs.getString("firstName"),rs.getString("lastName"),
             		   rs.getString("mobileNumber"),rs.getString("emailID"),rs.getString("joiningDate"),rs.getString("activeStatus"));
                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public Employee getEmployeeById(int empId) {
        
    	Employee emp=null;;
    	
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String sqlfetch = "SELECT * FROM employee WHERE ID = ?";
            PreparedStatement pstmt = sqlConn.prepareStatement(sqlfetch);
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
               emp =new Employee(rs.getInt("ID"),rs.getString("firstName"),rs.getString("lastName"),
            		   rs.getString("mobileNumber"),rs.getString("emailID"),rs.getString("joiningDate"),rs.getString("activeStatus")); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return emp;
       
    }

    public boolean deleteEmployee(int empId) {
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String stmt = "DELETE FROM employee WHERE id = ?";
            PreparedStatement pstmt = sqlConn.prepareStatement(stmt);
            pstmt.setInt(1, empId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(int empid, String nFirstName, String nLastName) {
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String updatestmt = "UPDATE employee SET firstName = ?, lastName = ? WHERE id = ?";
            PreparedStatement pstmt = sqlConn.prepareStatement(updatestmt);

            pstmt.setString(1, nFirstName);
            pstmt.setString(2, nLastName);
            pstmt.setInt(3, empid);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
