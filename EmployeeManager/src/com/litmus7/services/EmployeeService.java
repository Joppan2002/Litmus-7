package com.litmus7.employeemanager.services;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exceptions.EmployeeDAOException;
import com.litmus7.employeemanager.exceptions.EmployeeNotFoundException;
import com.litmus7.employeemanager.exceptions.EmployeeServiceException;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.*;

import com.litmus7.employeemanager.constants.errorCode;
import com.litmus7.employeemanager.dao.employeeDAO;
import com.litmus7.employeemanager.util.Response;


public class EmployeeService 
{
	
	private static final Logger logger = LogManager.getLogger(EmployeeService.class);
	
	employeeDAO employeedao=new employeeDAO();
	public Response<Integer, Boolean, ?> addEmployee(Employee emp) throws EmployeeServiceException
	{
		 logger.trace("Creating employee: " + emp.getId());
		try {
		
		int k=employeedao.createEmployee(emp);
		
			Response <Integer,Boolean,?> response= new Response<>(k,true,null);
			logger.info("Employee created successfully: " + emp.getId());
			return response;
		}
		catch(EmployeeDAOException e)
		{
			 logger.error(errorCode.ES101 + emp.getId(), e);
			 Response <Integer,Boolean,String> response= new Response<>(0,false,errorCode.ES101);
			 return response;
//			 
//			throw new EmployeeServiceException("Employee Creation failed at Service Layer",errorCode.ES101,e);
		}
	}
	
	public Response<List <Employee>,Boolean,String> RetrieveAll() throws EmployeeServiceException
	{
		logger.trace("Trying to fetch details of all employees");
		try {
		List<Employee> employees = new ArrayList<>(employeedao.getAllEmployee());
		if(employees.size()>0)
		{
		Response<List <Employee>,Boolean,String> response=new Response<>(employees,true,null);
		logger.info("Retrieval of details of all employees successfull");
		return response;
		}
		else
		{
			Response<List <Employee>,Boolean,String> response=new Response<>(null,false,null);
			logger.error(errorCode.ES102);
			return response;
		}
		}
		catch(EmployeeDAOException e)
		{
			logger.error("Retrieval of details of all employees successfull");
			Response<List <Employee>,Boolean,String> response=new Response<>(null,false,errorCode.ES102);
			return response;
			//throw new EmployeeServiceException("Whole employee fetching failed at Service Layer",errorCode.ES102,e);
		}
		
	}
	
	public Response<Employee,Boolean,String> RetrieveOne(int ID) throws EmployeeServiceException, EmployeeNotFoundException
	{
		logger.info("Trying to fetch details of employee with id : "+ID);
		try {
		Employee emp=employeedao.getEmployeeById(ID);
		if(emp!=null) {
			Response<Employee,Boolean,String> response=new Response<>(emp,true,null);
			logger.info("Retrieved details of employee with ID : "+ID);
			return response;
		}
		else
		{
			logger.error("Employee with "+ID+" not found");
			Response<Employee,Boolean,String> response=new Response<>(null,false,errorCode.ES103);
			return response;
		}
		}
		catch(EmployeeDAOException e)
		{
			logger.error("Employee details fetching failed");
			Response<Employee,Boolean,String> response=new Response<>(null,false,errorCode.ES103);
			return response;
//			throw new EmployeeServiceException("Employee fetching failed at Service Layer",errorCode.ES103,e);
		}
	}
	
	public Response<?,Boolean,String> DeleteByID(int ID) throws EmployeeServiceException
	{
		logger.info("Trying to delete employee with ID :"+ID);
		try {
		Response<?,Boolean,String> response=new Response<>(null,employeedao.deleteEmployee(ID),null);
		logger.info("Deleted employee with ID :"+ID);
		return response;
		}
		catch(EmployeeDAOException e)
		{
			logger.error("Deletion failed");
			Response<?,Boolean,String> response=new Response<>(null,false,errorCode.ES104);
			return response;
//			throw new EmployeeServiceException("Employee fetching failed at Service Layer",errorCode.ES104,e);
		}
		
	}
	
	public Response<?,Boolean,String> UpdateName(int ID,String newFirstName,String newLastName) throws EmployeeServiceException
	{
		logger.info("Trying to update the name of employee with ID : "+ID);
		try
		{
			Response<?,Boolean,String> response=new Response<>(null,employeedao.updateEmployee(ID, newFirstName, newLastName),null);
			logger.info("Updated employee name");
			return response;
		}
		catch(EmployeeDAOException e)
		{
			logger.error("Name updation failed");
			Response<?,Boolean,String> response=new Response<>(null,false,errorCode.ES105);
			return response;
			//throw new EmployeeServiceException("Employee fetching failed at Service Layer",errorCode.ES105,e);
		}
	}
	
}
