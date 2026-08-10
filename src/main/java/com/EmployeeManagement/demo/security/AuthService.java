package com.EmployeeManagement.demo.security;

import com.EmployeeManagement.demo.dtos.AuthRequestDTO;
import com.EmployeeManagement.demo.dtos.AuthResponseDTO;
import com.EmployeeManagement.demo.dtos.EmployeeRequestDTO;
import com.EmployeeManagement.demo.dtos.EmployeeResponseDTO;
import com.EmployeeManagement.demo.entities.Employee;
import com.EmployeeManagement.demo.mappers.EmployeeMapper;
import com.EmployeeManagement.demo.repositories.EmployeeRepository;
import com.EmployeeManagement.demo.services.EmployeeService;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;
    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;

    public AuthService(EmployeeService employeeService, PasswordEncoder passwordEncoder, EmployeeMapper employeeMapper, JwtService jwtService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
        this.jwtService = jwtService;
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponseDTO registerEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeMapper.toEntity(employeeRequestDTO);
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        employeeService.createEmployee(employee);
        return employeeMapper.toResponseDto(employee);
    }

    public AuthResponseDTO loginEmployee(AuthRequestDTO authRequestDTO) throws JOSEException {
        Long employeeId;
        String employeeName = authRequestDTO.getName() != null ? authRequestDTO.getName() : null;
        String employeeEmail = authRequestDTO.getEmail() != null ? authRequestDTO.getEmail() : null;
        String password = authRequestDTO.getPassword();
        if(employeeName == null) {
            employeeId = employeeRepository.findIdByEmail(employeeEmail);
        } else {
            employeeId = employeeRepository.findIdByName(employeeName);
        }

        VerifyEmployee(employeeName,employeeName,password);
        if(authRequestDTO != null) {

            jwtService.generateAccessToken(employeeId,employeeName, employeeEmail);


        }
    }

    private Boolean VerifyEmployee(String name, String email, String password) {

    }
}
