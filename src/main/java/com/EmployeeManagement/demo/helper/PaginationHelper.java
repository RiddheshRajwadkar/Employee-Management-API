package com.EmployeeManagement.demo.helper;

import com.EmployeeManagement.demo.dtos.PageResponseDTO;

import java.util.List;

public class PaginationHelper {

    public static <T> PageResponseDTO<T> createPaginationResponse(
            List<T> content,
            int page,
            int pageSize,
            int totalPages,
            long totalElements,
            boolean isLast,
            String message) {

        PageResponseDTO<T> response = new PageResponseDTO<>();
        response.setContent(content);
        response.setPage(page);
        response.setPageSize(pageSize);
        response.setTotalPages(totalPages);
        response.setTotalElements(totalElements);
        response.setIsLast(isLast);
        response.setMessage(message);
        return response;
    }

    public static <T> PageResponseDTO<T> createPaginationResponse(
            List<T> content,
            int page,
            int pageSize,
            int totalPages,
            long totalElements,
            boolean isLast,
            String sortBy,
            String sortDirection,
            String message) {

        PageResponseDTO<T> response = createPaginationResponse(content, page, pageSize, totalPages, totalElements, isLast, message);
        response.setSortBy(sortBy);
        response.setSortDirection(sortDirection);
        return response;
    }
}
