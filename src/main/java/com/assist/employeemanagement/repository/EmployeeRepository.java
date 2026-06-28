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

}
