package com.assist.employeemanagement.service;


import com.assist.employeemanagement.model.Leave;
import com.assist.employeemanagement.model.Employee;

import java.util.List;
import java.util.Map;

public interface LeaveService {

    // Apply leave
    Leave applyLeave(Leave leave);

    // Get all leaves for an employee
    List<Leave> getLeavesByEmployee(Employee employee);

    // Get all pending leaves
    List<Leave> getPendingLeaves();

    // Approve leave
    Leave approveLeave(Long leaveId, String approvedBy);

    // Reject leave
    Leave rejectLeave(Long leaveId);

    // Get leave balance for employee
    Map<String, Integer> getLeaveBalance(Employee employee);

    // Get leave stats (for admin dashboard)
    Map<String, Long> getLeaveStats();

    // Get leave by id
    Leave getLeaveById(Long id);
}
