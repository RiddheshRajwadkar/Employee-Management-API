package com.EmployeeManagement.demo.security;

import com.EmployeeManagement.demo.dtos.AuthRequestDTO;
import com.EmployeeManagement.demo.dtos.AuthResponseDTO;
import com.EmployeeManagement.demo.dtos.EmployeeRequestDTO;
import com.EmployeeManagement.demo.dtos.EmployeeResponseDTO;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(security = @SecurityRequirement(name = ""))
    public ResponseEntity<EmployeeResponseDTO> registerEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO){
        EmployeeResponseDTO createEmployeeDto = authService.registerEmployee(employeeRequestDTO);
        return new ResponseEntity<>(createEmployeeDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(security = @SecurityRequirement(name = ""))
    public ResponseEntity<AuthResponseDTO> loginEmployee(@RequestBody AuthRequestDTO authRequestDTO) throws JOSEException {
        AuthResponseDTO loginEmployeeDto = authService.loginEmployee(authRequestDTO);
        return new ResponseEntity<>(loginEmployeeDto, HttpStatus.ACCEPTED);
    }
}
