package com.EmployeeManagement.demo.repositories;

import com.EmployeeManagement.demo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {

    Employee findEmployeeByEmail(String email);

    Long findIdByEmail(String email);

    Long findIdByName(String name);
}
