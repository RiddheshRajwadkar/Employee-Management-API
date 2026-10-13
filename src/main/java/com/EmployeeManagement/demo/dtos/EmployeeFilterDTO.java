package com.EmployeeManagement.demo.dtos;

import java.math.BigDecimal;

public class EmployeeFilterDTO {

    private String name;

    private String department;

    private BigDecimal salary;

    private BigDecimal salaryFrom;

    private BigDecimal salaryTo;

    private String email;

    private String role;

    public EmployeeFilterDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getSalaryFrom() {
        return salaryFrom != null ? salaryFrom : salary;
    }

    public void setSalaryFrom(BigDecimal salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public BigDecimal getSalaryTo() {
        return salaryTo != null ? salaryTo : salary;
    }

    public void setSalaryTo(BigDecimal salaryTo) {
        this.salaryTo = salaryTo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
