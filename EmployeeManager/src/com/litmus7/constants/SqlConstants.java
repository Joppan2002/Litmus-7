package com.litmus7.employeemanager.constants;

public class SqlConstants 
{

	private SqlConstants()
	{
		
	}
	
	public static final String Select_All_without_condition="select ID,firstName,lastName,mobileNumber,emailID,joiningDate,activeStatus from employee";
	
	public static final String CreateEmployees="INSERT INTO employee(id,firstName,lastName,mobileNumber,emailId,joiningDate,activeStatus) VALUES (?,?,?,?,?,?,?)";
	
	public static final String Select_Employee_By_ID="select ID,firstName,lastName,mobileNumber,emailID,joiningDate,activeStatus from employee where ID=?";
	
	public static final String Delete_Employee_By_ID="DELETE FROM employee WHERE id = ?";
	
	public static final String Update_Employee_Name="UPDATE employee SET firstName = ?, lastName = ? WHERE id = ?";
}
