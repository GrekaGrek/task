package com.ergo.task.repository.specification;

import com.ergo.task.domain.PersonDataEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonDataSpecificationTest {

    public static final String NAME = "Adam";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.parse("1972-12-12");
    public static final String ATTRIBUTE_NAME = "firstName";
    public static final String ATTRIBUTE_DATE = "dateOfBirth";
    @Mock
    private Root<PersonDataEntity> mockRoot;
    @Mock
    private CriteriaQuery<?> mockQuery;
    @Mock
    private CriteriaBuilder mockCriteriaBuilder;

    @Captor
    private ArgumentCaptor<Predicate> argumentCaptor;

    private final PersonDataSpecification specification = new PersonDataSpecification();

    @Test
    void findPersonByFirstName() {
        PersonSearchCriteria filter = new PersonSearchCriteria(NAME, null);

        Path namePathMock = mock(Path.class);
        when(mockRoot.get(ATTRIBUTE_NAME)).thenReturn(namePathMock);

        Predicate personNamePredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(any(Expression.class), eq(NAME))).thenReturn(personNamePredicate);

        Predicate finalPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.and(any())).thenReturn(finalPredicate);

        Specification<PersonDataEntity> result = specification.findPersonByNameOrBOD(filter);

        Predicate predicate = result.toPredicate(mockRoot, mockQuery, mockCriteriaBuilder);
        verify(mockRoot).get(ATTRIBUTE_NAME);
        verify(mockCriteriaBuilder).equal(any(Expression.class), eq(NAME));
        verify(mockCriteriaBuilder, times(2)).and(argumentCaptor.capture());

        List<Predicate> allValues = argumentCaptor.getAllValues();
        verifyNoMoreInteractions(mockCriteriaBuilder);

        assertThat(allValues).hasSize(2);
        assertThat(predicate).isEqualTo(finalPredicate);
    }

    @Test
    void findPersonByDateOfBirth() {
        PersonSearchCriteria filter = new PersonSearchCriteria(null, DATE_OF_BIRTH.toString());

        Path namePathMock = mock(Path.class);
        when(mockRoot.get(ATTRIBUTE_DATE)).thenReturn(namePathMock);

        Predicate personDatePredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(any(Expression.class), eq(DATE_OF_BIRTH))).thenReturn(personDatePredicate);

        Predicate finalPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.and(any())).thenReturn(finalPredicate);

        Specification<PersonDataEntity> result = specification.findPersonByNameOrBOD(filter);

        Predicate predicate = result.toPredicate(mockRoot, mockQuery, mockCriteriaBuilder);
        verify(mockRoot).get(ATTRIBUTE_DATE);
        verify(mockCriteriaBuilder).equal(any(Expression.class), eq(DATE_OF_BIRTH));
        verify(mockCriteriaBuilder, times(2)).and(argumentCaptor.capture());

        List<Predicate> allValues = argumentCaptor.getAllValues();
        verifyNoMoreInteractions(mockCriteriaBuilder);

        assertThat(allValues).hasSize(2);
        assertThat(predicate).isEqualTo(finalPredicate);
    }
    @Test
    void findPersonByFirstNameAndDateOfBirth() {
        PersonSearchCriteria filter = new PersonSearchCriteria(NAME, DATE_OF_BIRTH.toString());

        Path namePathMock = mock(Path.class);
        when(mockRoot.get(ATTRIBUTE_NAME)).thenReturn(namePathMock);
        when(mockRoot.get(ATTRIBUTE_DATE)).thenReturn(namePathMock);

        Predicate personPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(any(Expression.class), eq(NAME))).thenReturn(personPredicate);
        Predicate personDatePredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(any(Expression.class), eq(DATE_OF_BIRTH))).thenReturn(personDatePredicate);

        Predicate finalPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.and(any())).thenReturn(finalPredicate);

        Specification<PersonDataEntity> result = specification.findPersonByNameOrBOD(filter);

        Predicate predicate = result.toPredicate(mockRoot, mockQuery, mockCriteriaBuilder);
        verify(mockRoot).get(ATTRIBUTE_NAME);
        verify(mockRoot).get(ATTRIBUTE_DATE);
        verify(mockCriteriaBuilder).equal(any(Expression.class), eq(NAME));
        verify(mockCriteriaBuilder).equal(any(Expression.class), eq(DATE_OF_BIRTH));
        verify(mockCriteriaBuilder, times(3)).and(argumentCaptor.capture());

        List<Predicate> allValues = argumentCaptor.getAllValues();
        verifyNoMoreInteractions(mockCriteriaBuilder);

        assertThat(allValues).hasSize(4);
        assertThat(predicate).isEqualTo(finalPredicate);
    }
}