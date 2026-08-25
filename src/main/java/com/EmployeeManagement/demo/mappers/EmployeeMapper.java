package com.EmployeeManagement.demo.mappers;

import com.EmployeeManagement.demo.dtos.EmployeeRequestDTO;
import com.EmployeeManagement.demo.dtos.EmployeeResponseDTO;
import com.EmployeeManagement.demo.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO dto);

    EmployeeRequestDTO toDto(Employee entity);

    EmployeeResponseDTO toResponseDto(Employee entity);
}
