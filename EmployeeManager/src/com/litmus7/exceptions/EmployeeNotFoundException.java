package com.litmus7.employeemanager.exceptions;

public class EmployeeNotFoundException extends Exception 
{
	String errorCode;
	public EmployeeNotFoundException(String message,String errorCode) 
	{
		super(message);
		this.errorCode=errorCode;
	}
	
	String getErrorCode()
	{
		return errorCode;
	}
	
}
