package com.ergo.task.repository.specification;

import com.ergo.task.domain.PersonDataEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDataSpecification {
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    public static final String FIRST_NAME = "firstName";

    public Specification<PersonDataEntity> findPersonByNameOrBOD(PersonSearchCriteria filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.firstName() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(FIRST_NAME), filter.firstName())));
            }
            if (filter.dateOfBirth() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(DATE_OF_BIRTH), LocalDate.parse(filter.dateOfBirth()))));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
