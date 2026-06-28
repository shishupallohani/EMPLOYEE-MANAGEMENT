package com.assist.employeemanagement.service;

import java.util.List;
import java.util.Map;

import com.assist.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;


public interface EmployeeService {
	List<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	void deleteEmployeeById(long id);
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

	// Location wise employee count
	Map<String, Long> getEmployeeCountByLocation();

	// Location wise employees list
	List<Employee> getEmployeesByLocation(String location);
}
