package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Employee;

public interface IEmployeeService {

	void saveEmployee(Employee employee);

	List<Employee> findAllEmployees();

	void deleteEmployeeBySsn(String ssn);

	Employee findBySsn(String ssn);

	void updateEmployee(Employee employee);
}
