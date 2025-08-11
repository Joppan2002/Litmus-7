package com.litmus7.employeemanager.services;

import com.litmus7.employeemanager.dto.Employee;

import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dao.employeeDAO;
import com.litmus7.employeemanager.util.Response;

public class EmployeeService 
{
	employeeDAO employeedao=new employeeDAO();
	public Response<Integer, Boolean, ?> addEmployee(Employee emp)
	{
		
		int k=employeedao.createEmployee(emp);
		
			Response <Integer,Boolean,?> response= new Response<>(k,true,null);
			return response;
	}
	
	public Response<List <Employee>,Boolean,Integer> RetrieveAll()
	{
		List<Employee> employees = new ArrayList<>(employeedao.getAllEmployee());
		if(employees.size()>0)
		{
		Response<List <Employee>,Boolean,Integer> response=new Response<>(employees,true,employees.size());
		return response;
		}
		else
		{
			Response<List <Employee>,Boolean,Integer> response=new Response<>(null,false,employees.size());
			return response;
		}
		
	}
	
	public Response<Employee,Boolean,?> RetrieveOne(int ID)
	{
		Employee emp=employeedao.getEmployeeById(ID);
		if(emp!=null) {
			Response<Employee,Boolean,?> response=new Response<>(emp,true,null);
			return response;
		}
		else
		{
			return null;
		}
		
	}
	
	public Response<?,Boolean,?> DeleteByID(int ID)
	{
		Response<?,Boolean,?> response=new Response<>(null,employeedao.deleteEmployee(ID),null);
		return response;
	}
	
	public Response<?,Boolean,?> UpdateName(int ID,String newFirstName,String newLastName)
	{
		Response<?,Boolean,?> response=new Response<>(null,employeedao.updateEmployee(ID, newFirstName, newLastName),null);
		return response;
	}
	
}
