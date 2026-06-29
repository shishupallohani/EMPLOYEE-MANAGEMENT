package com.assist.employeemanagement.service;


import com.assist.employeemanagement.model.Leave;
import com.assist.employeemanagement.model.Employee;
import com.assist.employeemanagement.repository.LeaveRepository;
import com.assist.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Leave applyLeave(Leave leave) {
        leave.setStatus("PENDING");
        leave.setAppliedOn(LocalDate.now());

        long days = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
        leave.setReason(leave.getReason() + " (" + days + " days)");

        return leaveRepository.save(leave);
    }

    @Override
    public List<Leave> getLeavesByEmployee(Employee employee) {
        return leaveRepository.findByEmployeeOrderByAppliedOnDesc(employee);
    }

    @Override
    public List<Leave> getPendingLeaves() {
        return leaveRepository.findPendingLeavesWithEmployee();
    }

    @Override
    @Transactional
    public Leave approveLeave(Long leaveId, String approvedBy) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus("APPROVED");
        leave.setApprovedOn(LocalDate.now());
        leave.setApprovedBy(approvedBy);

        // Update employee leave balance
        Employee employee = leave.getEmployee();
        long days = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;

        employee.setUsedLeaves(employee.getUsedLeaves() + (int) days);
        employee.setRemainingLeaves(employee.getRemainingLeaves() - (int) days);
        employeeRepository.save(employee);

        return leaveRepository.save(leave);
    }

    @Override
    @Transactional
    public Leave rejectLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus("REJECTED");
        leave.setApprovedOn(LocalDate.now());

        return leaveRepository.save(leave);
    }

    @Override
    public Map<String, Integer> getLeaveBalance(Employee employee) {
        Map<String, Integer> balance = new HashMap<>();
        balance.put("total", employee.getTotalLeaves());
        balance.put("used", employee.getUsedLeaves());
        balance.put("remaining", employee.getRemainingLeaves());
        return balance;
    }

    @Override
    public Map<String, Long> getLeaveStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("pending", leaveRepository.countByStatus("PENDING"));
        stats.put("approved", leaveRepository.countByStatus("APPROVED"));
        stats.put("rejected", leaveRepository.countByStatus("REJECTED"));
        stats.put("total", leaveRepository.count());
        return stats;
    }

    @Override
    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
    }
}
