package com.EmployeeManagement.demo.dtos;

import com.EmployeeManagement.demo.config.AppConstants;

import java.util.List;

public class PageResponseDTO<T> {

    private List<T> content;
    private Integer page = AppConstants.DEFAULT_PAGE_NUMBER;
    private Integer pageSize = AppConstants.DEFAULT_PAGE_SIZE;
    private Long totalElements;
    private Integer totalPages;
    private Boolean isLast;
    private String sortBy = AppConstants.DEFAULT_SORT_BY;
    private String sortDirection = AppConstants.DEFAULT_SORT_DIRECTION;
    private String message;

    public PageResponseDTO() {
    }

    public PageResponseDTO(List<T> content, Integer page, Integer pageSize, Long totalElements, Integer totalPages, Boolean isLast, String sortBy, String sortDirection, String message) {
        this.content = content;
        this.page = page;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.message = message;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(Boolean last) {
        isLast = last;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
