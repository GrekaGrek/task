package com.ergo.task.repository.specification;

public record PersonSearchCriteria (
    String firstName,
    String dateOfBirth
) {
}
