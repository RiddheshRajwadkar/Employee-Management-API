package com.EmployeeManagement.demo.mappers;

import com.EmployeeManagement.demo.dtos.EmployeeRequestDTO;
import com.EmployeeManagement.demo.dtos.EmployeeResponseDTO;
import com.EmployeeManagement.demo.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO dto);

    EmployeeRequestDTO toDto(Employee entity);

    List<EmployeeResponseDTO> toResponseDtos(List<Employee> entities);

    EmployeeResponseDTO toResponseDto(Employee entity);
}
