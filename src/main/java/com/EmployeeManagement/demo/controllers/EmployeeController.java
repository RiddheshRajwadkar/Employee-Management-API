package com.EmployeeManagement.demo.controllers;

import com.EmployeeManagement.demo.dtos.EmployeeFilterDTO;
import com.EmployeeManagement.demo.dtos.EmployeeResponseDTO;
import com.EmployeeManagement.demo.dtos.PageResponseDTO;
import com.EmployeeManagement.demo.entities.Employee;
import com.EmployeeManagement.demo.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee") // Base URL for all endpoints in this controller
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Create a new employee")

    @ApiResponse(responseCode = "201", description = "Employee created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid employee data")

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED); // Return 201 status
    }

    @Operation(summary = "Get all employees")

    @ApiResponse(responseCode = "200", description = "Employees retrieved successfully")

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK); // Return 200 status
    }

    @Operation(summary = "Search employees")

    @ApiResponse(responseCode = "200", description = "Employees found successfully")
    @ApiResponse(responseCode = "400", description = "Invalid search filter")

    @PostMapping(value = "/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageResponseDTO<EmployeeResponseDTO>> searchEmployees(
            EmployeeFilterDTO employeeFilterDTO,
            PageResponseDTO pageResponseDTO
    ) {
        PageResponseDTO<EmployeeResponseDTO> employees = employeeService.searchEmployees(employeeFilterDTO, pageResponseDTO);
        return new ResponseEntity<>(employees, HttpStatus.OK); // Return 200 status
    }

    @Operation(summary = "Get employee by ID")

    @ApiResponse(responseCode = "200", description = "Employee found successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK); // Return 200 status
    }

    @Operation(summary = "Update employee")

    @ApiResponse(responseCode = "200", description = "Employee updated successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @ApiResponse(responseCode = "400", description = "Invalid employee data")

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee updatedEmployee
    ) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        return new ResponseEntity<>(employee, HttpStatus.OK); // Return 200 status
    }

    @Operation(summary = "Delete employee")

    @ApiResponse(responseCode = "204", description = "Employee deleted successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 status
    }
}