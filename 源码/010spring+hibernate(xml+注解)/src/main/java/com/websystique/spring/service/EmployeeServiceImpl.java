package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.IEmployeeDao;
import com.websystique.spring.model.Employee;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao employeeDao;
	
	public void saveEmployee(Employee employee) {
		employeeDao.saveEmployee(employee);
	}

	public List<Employee> findAllEmployees() {
		return employeeDao.findAllEmployees();
	}

	public void deleteEmployeeBySsn(String ssn) {
		employeeDao.deleteEmployeeBySsn(ssn);
	}

	public Employee findBySsn(String ssn) {
		return employeeDao.findBySsn(ssn);
	}

	public void updateEmployee(Employee employee){
		employeeDao.updateEmployee(employee);
	}
}
