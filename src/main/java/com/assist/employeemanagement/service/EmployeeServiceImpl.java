package com.assist.employeemanagement.service;

import java.util.*;

import com.assist.employeemanagement.model.Employee;
import com.assist.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return employee;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}

	@Override
	public Map<String, Long> getEmployeeCountByLocation() {
		List<String> allLocations = Arrays.asList(
				"NOIDA", "DELHI", "AHAMDABAD", "PUNE", "MUMBAI",
				"BANGLORE", "CHENNAI", "PATNA", "GOA",
				"HYDERABAD", "KOLKATA", "LUCKNOW"
		);

		Map<String, Long> locationCountMap = new HashMap<>();

		for (String location : allLocations) {
			// Case insensitive count
			Long count = employeeRepository.countByLocationIgnoreCase(location);
			locationCountMap.put(location, count != null ? count : 0L);
		}

		return locationCountMap;
	}

	@Override
	public List<Employee> getEmployeesByLocation(String location) {
		return employeeRepository.findByLocationIgnoreCase(location);
	}
}
