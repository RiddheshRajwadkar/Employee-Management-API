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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(EmployeeService employeeService, PasswordEncoder passwordEncoder, EmployeeMapper employeeMapper, EmployeeRepository employeeRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public EmployeeResponseDTO registerEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeRepository.findEmployeeByEmail(employeeRequestDTO.getEmail());
        if(employee != null){
            throw new RuntimeException("Employee with email " + employeeRequestDTO.getEmail() + " already exists");
        }
        employee = employeeMapper.toEntity(employeeRequestDTO);
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        employee.setStatus("ACTIVE");
        employeeService.createEmployee(employee);
        return employeeMapper.toResponseDto(employee);
    }

    public AuthResponseDTO loginEmployee(AuthRequestDTO authRequestDTO) throws JOSEException {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));

        Employee employee = (Employee) authentication.getPrincipal();

        String token = jwtService.generateAccessToken(employee);
        return new AuthResponseDTO(token);
    }
}
