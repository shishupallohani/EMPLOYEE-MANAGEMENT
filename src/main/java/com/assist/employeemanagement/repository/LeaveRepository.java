package com.assist.employeemanagement.repository;


import com.assist.employeemanagement.model.Leave;
import com.assist.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    // Get all leaves for a specific employee (sorted by applied date desc)
    List<Leave> findByEmployeeOrderByAppliedOnDesc(Employee employee);

    // Get pending leaves (for admin)
    List<Leave> findByStatusOrderByAppliedOnAsc(String status);

    // Get leaves by status for a specific employee
    List<Leave> findByEmployeeAndStatus(Employee employee, String status);

    // Count pending leaves
    @Query("SELECT COUNT(l) FROM Leave l WHERE l.status = 'PENDING'")
    long countPendingLeaves();

    // Count by status
    long countByStatus(String status);

    @Query("SELECT l FROM Leave l JOIN FETCH l.employee WHERE l.status = 'PENDING' ORDER BY l.appliedOn ASC")
    List<Leave> findPendingLeavesWithEmployee();

}
