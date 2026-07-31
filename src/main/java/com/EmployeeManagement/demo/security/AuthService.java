package com.EmployeeManagement.demo.security;

import com.EmployeeManagement.demo.dtos.employeeRequestDTO;
import com.EmployeeManagement.demo.dtos.employeeResponseDTO;
import com.EmployeeManagement.demo.entities.Employee;
import com.EmployeeManagement.demo.mappers.employeeMapper;
import com.EmployeeManagement.demo.services.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final employeeMapper employeeMapper;

    public AuthService(EmployeeService employeeService, PasswordEncoder passwordEncoder, employeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    public employeeResponseDTO registerEmployee(employeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeMapper.toEntity(employeeRequestDTO);
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        employeeService.createEmployee(employee);
        return employeeMapper.toResponseDto(employee);
    }
}
