package com.EmployeeManagement.demo.services;

import com.EmployeeManagement.demo.config.AppConstants;
import com.EmployeeManagement.demo.dtos.EmployeeFilterDTO;
import com.EmployeeManagement.demo.dtos.EmployeeResponseDTO;
import com.EmployeeManagement.demo.dtos.PageResponseDTO;
import com.EmployeeManagement.demo.entities.Employee;
import com.EmployeeManagement.demo.exception.ResourceNotFoundException;
import com.EmployeeManagement.demo.mappers.EmployeeMapper;
import com.EmployeeManagement.demo.helper.PaginationHelper;
import com.EmployeeManagement.demo.repositories.EmployeeRepository;
import com.EmployeeManagement.demo.specification.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public PageResponseDTO<EmployeeResponseDTO> searchEmployees(EmployeeFilterDTO employeeFilterDTO, PageResponseDTO pageResponseDTO) {
        Specification<Employee> spec = EmployeeSpecification.getSpecification(employeeFilterDTO);

        String sortBy = (pageResponseDTO != null && pageResponseDTO.getSortBy() != null)
                ? pageResponseDTO.getSortBy()
                : AppConstants.DEFAULT_SORT_BY;

        String sortDirection = (pageResponseDTO != null && pageResponseDTO.getSortDirection() != null)
                ? pageResponseDTO.getSortDirection()
                : AppConstants.DEFAULT_SORT_DIRECTION;

        Sort sort = Sort.by("desc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC, sortBy);

        Integer pageSize = (pageResponseDTO != null) ? pageResponseDTO.getPageSize() : AppConstants.DEFAULT_PAGE_SIZE;
        Integer pageNumber = (pageResponseDTO != null) ? pageResponseDTO.getPage() : AppConstants.DEFAULT_PAGE_NUMBER;

        return (pageSize != null && pageSize == -1)
                ? getAllEmployees(spec, sort)
                : getPaginatedEmployees(spec, sort, pageNumber, pageSize);
    }

    public PageResponseDTO<EmployeeResponseDTO> getAllEmployees(Specification<Employee> spec, Sort sort) {
        List<Employee> entityList = employeeRepository.findAll(spec, sort);

        String message = entityList.isEmpty()
                ? "No records found."
                : "Employees fetched successfully!";

        String sortBy = sort.iterator().hasNext() ? sort.iterator().next().getProperty() : AppConstants.DEFAULT_SORT_BY;
        String sortDirection = sort.iterator().hasNext() ? sort.iterator().next().getDirection().name() : AppConstants.DEFAULT_SORT_DIRECTION;

        return PaginationHelper.createPaginationResponse(
                employeeMapper.toResponseDtos(entityList),
                0,
                entityList.size(),
                entityList.isEmpty() ? 0 : 1,
                (long) entityList.size(),
                true,
                sortBy,
                sortDirection,
                message
        );
    }

    private PageResponseDTO<EmployeeResponseDTO> getPaginatedEmployees(Specification<Employee> spec, Sort sort, Integer page, Integer pageSize) {
        int pageNumber = (page != null && page >= 0) ? page : AppConstants.DEFAULT_PAGE_NUMBER;
        int size = (pageSize != null && pageSize > 0) ? pageSize : AppConstants.DEFAULT_PAGE_SIZE;

        Pageable pageable = PageRequest.of(pageNumber, size, sort);
        Page<Employee> employeePage = employeeRepository.findAll(spec, pageable);

        String message = employeePage.isEmpty()
                ? "No records found."
                : "Employees fetched successfully!";

        String sortBy = sort.iterator().hasNext() ? sort.iterator().next().getProperty() : AppConstants.DEFAULT_SORT_BY;
        String sortDirection = sort.iterator().hasNext() ? sort.iterator().next().getDirection().name() : AppConstants.DEFAULT_SORT_DIRECTION;

        return PaginationHelper.createPaginationResponse(
                employeeMapper.toResponseDtos(employeePage.getContent()),
                employeePage.getNumber(),
                employeePage.getSize(),
                employeePage.getTotalPages(),
                employeePage.getTotalElements(),
                employeePage.isLast(),
                sortBy,
                sortDirection,
                message
        );
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
