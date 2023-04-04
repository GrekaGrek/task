package com.ergo.task.repository.specification;

import java.time.LocalDate;

public record PersonSearchCriteria (
    String firstName,
    LocalDate dateOfBirth
) {
}
