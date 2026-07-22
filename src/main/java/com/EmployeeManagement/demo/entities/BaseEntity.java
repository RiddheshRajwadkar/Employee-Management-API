package com.EmployeeManagement.demo.entities;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @CreationTimestamp
    private LocalDateTime createdDate;
    @CreationTimestamp
    private LocalDateTime updatedDate;
    private String status;

    public BaseEntity() {
    }

    public BaseEntity(LocalDateTime createdDate, LocalDateTime updatedDate, String status) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
