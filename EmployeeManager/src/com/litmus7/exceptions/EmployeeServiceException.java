package com.litmus7.employeemanager.exceptions;

public class EmployeeServiceException extends Exception 
{
	String errorCode;
	
	public EmployeeServiceException (String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode=errorCode;
	}
	
	public EmployeeServiceException (String message) {
		super(message);
	}
	
	String getErrorCode()
	{
		return errorCode;
	}
}
