package com.assist.employeemanagement.controller;


import com.assist.employeemanagement.model.Employee;
import com.assist.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class DashboardController {

    @Autowired
    private EmployeeService employeeService;

    // ===== DASHBOARD PAGE =====
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Total Employees
        long totalEmployees = employeeService.getAllEmployees().size();
        model.addAttribute("totalEmployees", totalEmployees);

        // Total Locations
        Map<String, Long> locationCounts = employeeService.getEmployeeCountByLocation();
        long totalLocations = locationCounts.values().stream().filter(count -> count > 0).count();
        model.addAttribute("totalLocations", totalLocations);

        // Location-wise counts
        model.addAttribute("locationCounts", locationCounts);

        // Recent Employees (Last 5)
        List<Employee> recentEmployees = employeeService.getRecentEmployees(5);
        model.addAttribute("recentEmployees", recentEmployees);

        return "dashboard";
    }

    // ===== SEARCH API =====
    @GetMapping("/api/search")
    @ResponseBody
    public List<Employee> searchEmployees(@RequestParam("q") String query) {
        return employeeService.searchEmployees(query);
    }
}
