package com.assist.employeemanagement.repository;

import com.assist.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    // Case insensitive count
    @Query("SELECT COUNT(e) FROM Employee e WHERE UPPER(e.location) = UPPER(:location)")
    Long countByLocationIgnoreCase(@Param("location") String location);

    // Specific location count
    Long countByLocation(String location);

    // Location wise employees (Case Insensitive)
    @Query("SELECT e FROM Employee e WHERE UPPER(e.location) = UPPER(:location)")
    List<Employee> findByLocationIgnoreCase(@Param("location") String location);


    // Recent 5 employees
    List<Employee> findTop5ByOrderByIdDesc();

    // Search employees
    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Employee> searchEmployees(@Param("query") String query);

}
