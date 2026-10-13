package com.EmployeeManagement.demo.specification;

import com.EmployeeManagement.demo.dtos.EmployeeFilterDTO;
import com.EmployeeManagement.demo.entities.Employee;
import com.EmployeeManagement.demo.helper.SpecificationHelper;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class EmployeeSpecification {

    public static Specification<Employee> getSpecification(EmployeeFilterDTO employeeFilterDTO) {
        if (employeeFilterDTO == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return hasEmployeeName(employeeFilterDTO.getName())
                .and(hasEmployeeDepartment(employeeFilterDTO.getDepartment()))
                .and(hasEmployeeSalaryFrom(employeeFilterDTO.getSalaryFrom()))
                .and(hasEmployeeSalaryTo(employeeFilterDTO.getSalaryTo()))
                .and(hasEmployeeEmail(employeeFilterDTO.getEmail()))
                .and(hasEmployeeRole(employeeFilterDTO.getRole()));
    }

    private static Specification<Employee> hasEmployeeName(String name) {
        return SpecificationHelper.hasValueLike("name", name);
    }

    public static Specification<Employee> hasEmployeeDepartment(String department) {
        return SpecificationHelper.hasValueEqual("department", department);
    }

    public static Specification<Employee> hasEmployeeSalaryFrom(BigDecimal salary) {
        return SpecificationHelper.hasValueGreaterThanOrEqual("salary", salary);
    }

    public static Specification<Employee> hasEmployeeSalaryTo(BigDecimal salary) {
        return SpecificationHelper.hasValueLessThanOrEqual("salary", salary);
    }

    public static Specification<Employee> hasEmployeeEmail(String email) {
        return SpecificationHelper.hasValueEqual("email", email);
    }

    public static Specification<Employee> hasEmployeeRole(String role) {
        return SpecificationHelper.hasValueEqual("role", role);
    }

}
