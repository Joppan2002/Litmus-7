package com.litmus7.exceptions;

public class EmployeeServiceException extends Exception {
	
	public EmployeeServiceException (String message) {
		super(message);
	}
	
	public EmployeeServiceException (String message, Throwable cause) {
		super(message, cause);
	}
}
