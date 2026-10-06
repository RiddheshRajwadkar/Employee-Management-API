package com.EmployeeManagement.demo.dtos;

import java.sql.Timestamp;

public class ErrorResponseDTO {

    private Timestamp timestamp;

    private Integer status;

    private String message;

    public ErrorResponseDTO(Timestamp timestamp, Integer status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
