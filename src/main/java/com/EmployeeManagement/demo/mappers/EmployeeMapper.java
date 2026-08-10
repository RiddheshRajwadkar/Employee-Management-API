package com.EmployeeManagement.demo.mappers;

import com.EmployeeManagement.demo.dtos.EmployeeRequestDTO;
import com.EmployeeManagement.demo.dtos.EmployeeResponseDTO;
import com.EmployeeManagement.demo.entities.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO dto);

    EmployeeRequestDTO toDto(Employee entity);

    EmployeeResponseDTO toResponseDto(Employee entity);
}
