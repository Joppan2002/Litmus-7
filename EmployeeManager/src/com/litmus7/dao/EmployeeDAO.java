package com.litmus7.employeemanager.dao;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import com.litmus7.employeemanager.util.DatabaseConnectionUtil;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exceptions.EmployeeDAOException;
import com.litmus7.employeemanager.exceptions.EmployeeNotFoundException;
import com.litmus7.employeemanager.constants.SqlConstants;


public class employeeDAO {

    public employeeDAO() {
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createEmployee(Employee emp)throws EmployeeDAOException {
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String sql =SqlConstants.CreateEmployees;
            PreparedStatement pstmt = sqlConn.prepareStatement(sql);

            pstmt.setInt(1, emp.getId());
            pstmt.setString(2, emp.getFirstName());
            pstmt.setString(3, emp.getLastName());
            pstmt.setString(4, emp.getMobileNumber());
            pstmt.setString(5, emp.getEmailID());
            pstmt.setDate(6, Date.valueOf(LocalDate.parse(emp.getJoiningDate())));
            pstmt.setBoolean(7, Boolean.parseBoolean(emp.getActiveStatus()));

            return pstmt.executeUpdate();

        } catch (SQLException e) 
        {
        	throw new EmployeeDAOException("Error occured at EmployeeDAO Layer while creating employee",e);
        }
    }

    public List<Employee> getAllEmployee() throws EmployeeDAOException {
        List<Employee> employees = new ArrayList<>();

        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String sqlfetch = SqlConstants.Select_All_without_condition;
            PreparedStatement stmt = sqlConn.prepareStatement(sqlfetch);
            ResultSet rs = stmt.executeQuery(sqlfetch);

            while (rs.next()) {
                Employee emp =new Employee(rs.getInt("ID"),rs.getString("firstName"),rs.getString("lastName"),
             		   rs.getString("mobileNumber"),rs.getString("emailID"),rs.getString("joiningDate"),rs.getString("activeStatus"));
                employees.add(emp);
            }

        } catch (SQLException e) {
        	throw new EmployeeDAOException("Error occured at EmployeeDAO Layer while fetching all employees",e);
        }

        return employees;
    }

    public Employee getEmployeeById(int empId)throws EmployeeDAOException, EmployeeNotFoundException
    {
        
    	Employee emp=null;;
    	
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String sqlfetch = SqlConstants.Select_Employee_By_ID;
            PreparedStatement pstmt = sqlConn.prepareStatement(sqlfetch);
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
               emp =new Employee(rs.getInt("ID"),rs.getString("firstName"),rs.getString("lastName"),
            		   rs.getString("mobileNumber"),rs.getString("emailID"),rs.getString("joiningDate"),rs.getString("activeStatus")); 
            }
            if(emp==null)
            {
            	throw new EmployeeNotFoundException("Couldn't find the employee");
            }

        }
        catch (SQLException e) 
        {
            throw new EmployeeDAOException("Error occurred at EmployeeDAO Layer while fetching employee with ID: " + empId, e);
        }
        
        return emp;
       
    }

    public boolean deleteEmployee(int empId) throws EmployeeDAOException
    {
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String stmt = SqlConstants.Delete_Employee_By_ID;
            PreparedStatement pstmt = sqlConn.prepareStatement(stmt);
            pstmt.setInt(1, empId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new EmployeeDAOException("Error occurred at EmployeeDAO Layer while deleting employee with ID: " + empId, e);
        }
    }

    public boolean updateEmployee(int empid, String nFirstName, String nLastName) throws EmployeeDAOException 
    {
        try (Connection sqlConn = DatabaseConnectionUtil.getConnection()) {
            String updatestmt =SqlConstants.Update_Employee_Name;
            PreparedStatement pstmt = sqlConn.prepareStatement(updatestmt);

            pstmt.setString(1, nFirstName);
            pstmt.setString(2, nLastName);
            pstmt.setInt(3, empid);

            return pstmt.executeUpdate() > 0;

        }
        catch (SQLException e) {
            throw new EmployeeDAOException("Error updating name at EmployeeDAO Layer", e);
        }
    }
}
