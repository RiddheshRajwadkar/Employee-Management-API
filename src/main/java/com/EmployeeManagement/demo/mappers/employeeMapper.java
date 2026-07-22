package com.EmployeeManagement.demo.mappers;

import com.EmployeeManagement.demo.dtos.employeeRequestDTO;
import com.EmployeeManagement.demo.dtos.employeeResponseDTO;
import com.EmployeeManagement.demo.entities.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface employeeMapper {

    Employee toEntity(employeeRequestDTO dto);

    employeeRequestDTO toDto(Employee entity);

    employeeResponseDTO toResponseDto(Employee entity);
}
