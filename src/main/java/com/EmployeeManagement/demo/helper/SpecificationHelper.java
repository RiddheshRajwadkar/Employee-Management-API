package com.EmployeeManagement.demo.helper;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationHelper {


    public static<T, U> Specification<T> hasValueEqual(String field, U value) {
        return (root, query, cb) -> {
            if(value == null || value.toString().isEmpty()){
                return cb.conjunction();
            }

            if(value instanceof String && value.toString().isEmpty()) {
                return cb.conjunction();
            }

            if(value instanceof Number) {
                Double numericValue = ((Number)value).doubleValue();
                if (numericValue == 0.0) {
                    return cb.conjunction();
                }
            }
            return cb.equal(root.get(field), value);
        };
    }

    public static<T, U extends Comparable<U>> Specification<T> hasValueGreaterThanOrEqual(String field, U value) {
        return (root, query, cb) -> {
            if(value == null){
                return cb.conjunction();
            }
        return cb.greaterThanOrEqualTo(root.get(field), value);
        };
    }

    public static<T, U extends Comparable<U>> Specification<T> hasValueLessThanOrEqual(String field, U value) {
        return (root, query, cb) -> {
            if(value == null) {
                return cb.conjunction();
            }
        return cb.lessThanOrEqualTo(root.get(field), value);
        };
    }

    public static<T, U> Specification<T> hasValueNotEqual(String field, U value) {
        return(root, query, cb) -> {
            if(value == null){
                return cb.conjunction();
            }

            if(value instanceof String && ((String) value).isEmpty()) {
                return cb.conjunction();
            }

            if(value instanceof Number) {
                Double numericValue = ((Number) value).doubleValue();
                    if (numericValue == 0.0) {
                        return cb.conjunction();
                    }
            }
        return cb.notEqual(root.get(field), value);
        };
    }

    public static <T, U> Specification<T> hasValueLike(String field, U value) {
        return (root, query, cb) -> {
            if(value == null || value.toString().isEmpty()){
                return cb.conjunction();
            }
        return cb.like(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%");
        };
    }
}
