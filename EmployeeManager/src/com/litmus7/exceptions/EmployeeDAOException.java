package com.litmus7.employeemanager.exceptions;



public class EmployeeDAOException extends Exception 
{
	
	String errorCode;
	
	public EmployeeDAOException(String message,Throwable cause)
	{
		super(message,cause);
	}
	
	public EmployeeDAOException(String message,String errorCode, Throwable cause)
	{
		super(message,cause);
		this.errorCode=errorCode;
		
	}
	
	String getErrorCode()
	{
		return errorCode;
	}
}
