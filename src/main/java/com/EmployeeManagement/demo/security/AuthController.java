package com.EmployeeManagement.demo.security;

import com.EmployeeManagement.demo.dtos.employeeRequestDTO;
import com.EmployeeManagement.demo.dtos.employeeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<employeeResponseDTO> registerEmployee(@RequestBody employeeRequestDTO employeeRequestDTO){
        employeeResponseDTO createdEmployeeDto = authService.registerEmployee(employeeRequestDTO);
        return new ResponseEntity<>(createdEmployeeDto, HttpStatus.CREATED);
    }
}
