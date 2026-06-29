package com.assist.employeemanagement.controller;


import com.assist.employeemanagement.model.Leave;
import com.assist.employeemanagement.model.Employee;
import com.assist.employeemanagement.service.LeaveService;
import com.assist.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private EmployeeService employeeService;

    // ===== APPLY LEAVE PAGE =====
    @GetMapping("/apply-leave/{id}")
    public String showApplyLeaveForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("leave", new Leave());
        model.addAttribute("balance", leaveService.getLeaveBalance(employee));
        return "apply_leave";
    }

    @PostMapping("/apply-leave")
    public String applyLeave(@ModelAttribute Leave leave,
                             @RequestParam Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        leave.setEmployee(employee);
        leaveService.applyLeave(leave);
        return "redirect:/my-leaves/" + employeeId;
    }

    // ===== MY LEAVES PAGE =====
    @GetMapping("/my-leaves/{id}")
    public String myLeaves(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        List<Leave> leaves = leaveService.getLeavesByEmployee(employee);
        Map<String, Integer> balance = leaveService.getLeaveBalance(employee);

        model.addAttribute("employee", employee);
        model.addAttribute("leaves", leaves);
        model.addAttribute("balance", balance);
        return "my_leaves";
    }

    // ===== LEAVE REQUESTS (ADMIN) =====
    @GetMapping("/leave-requests")
    public String leaveRequests(Model model) {
        List<Leave> pendingLeaves = leaveService.getPendingLeaves();
        Map<String, Long> stats = leaveService.getLeaveStats();

        model.addAttribute("pendingLeaves", pendingLeaves);
        model.addAttribute("stats", stats);
        return "leave_requests";
    }

    @GetMapping("/approve-leave/{id}")
    public String approveLeave(@PathVariable Long id) {
        leaveService.approveLeave(id, "Admin");
        return "redirect:/admin-dashboard";
    }

    @GetMapping("/reject-leave/{id}")
    public String rejectLeave(@PathVariable Long id) {
        leaveService.rejectLeave(id);
        return "redirect:/admin-dashboard";
    }


    // ===== ADMIN DASHBOARD PAGE =====
    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "admin_dashboard";
    }

    @GetMapping("/api/leave-requests/recent")
    @ResponseBody
    public List<Leave> getRecentLeaveRequests() {
        List<Leave> leaves = leaveService.getPendingLeaves();
        leaves.forEach(l -> l.getEmployee().getFirstName()); // Force load
        return leaves;
    }

}
