package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Employee;

public interface IEmployeeDao {

	void saveEmployee(Employee employee);
	
	List<Employee> findAllEmployees();
	
	void deleteEmployeeBySsn(String ssn);
	
	Employee findBySsn(String ssn);
	
	void updateEmployee(Employee employee);
}
