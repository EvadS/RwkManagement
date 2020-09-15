package com.se.management.repository.specification;

import org.springframework.data.jpa.domain.Specification;


public abstract class SearchSpecification<T, U> {

    private final String wildcard = "%";

    public abstract Specification<T> getFilter(U request);

    public String containsLowerCase(String searchField) {
        return wildcard + searchField.toLowerCase() + wildcard;
    }
}