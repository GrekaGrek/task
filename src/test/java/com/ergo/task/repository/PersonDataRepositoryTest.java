package com.ergo.task.repository;

import com.ergo.task.domain.PersonDataEntity;
import com.ergo.task.enums.PersonGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersonDataRepositoryTest {

    public static final String PERSONAL_ID = "12234455678";

    @Autowired
    private PersonDataRepository repository;

    @Test
    void findPersonByPersonalId() {
        var personData0 = new PersonDataEntity()
                .setPersonalId("12234455678")
                .setLastName("Test")
                .setFirstName("Test")
                .setGender(PersonGender.MALE)
                .setDateOfBirth(LocalDate.of(2001, 10, 12))
                .setPhoneNumber("+37122336789")
                .setEmail("test@test.com");

        repository.save(personData0);

        var personData1 = new PersonDataEntity()
                .setPersonalId("21237895443")
                .setLastName("Test")
                .setFirstName("Test")
                .setGender(PersonGender.MALE)
                .setDateOfBirth(LocalDate.of(2001, 10, 12))
                .setPhoneNumber("+37122336789")
                .setEmail("test@test.com");

        repository.save(personData1);

        var foundPerson = repository.findByPersonalId(PERSONAL_ID);

        assertThat(foundPerson)
                .isPresent()
                .isEqualTo(Optional.of(personData0));
    }

    @Test
    void findPersonByPersonalIdReturnNoRecord() {
        var personData0 = new PersonDataEntity()
                .setPersonalId("12234715672")
                .setLastName("Test")
                .setFirstName("Test")
                .setGender(PersonGender.MALE)
                .setDateOfBirth(LocalDate.of(2001, 10, 12))
                .setPhoneNumber("+37122336789")
                .setEmail("test@test.com");

        repository.save(personData0);

        var personData1 = new PersonDataEntity()
                .setPersonalId("21237895443")
                .setLastName("Test")
                .setFirstName("Test")
                .setGender(PersonGender.MALE)
                .setDateOfBirth(LocalDate.of(2001, 10, 12))
                .setPhoneNumber("+37122336789")
                .setEmail("test@test.com");

        repository.save(personData1);

        var foundPerson = repository.findByPersonalId(PERSONAL_ID);

        assertThat(foundPerson).isEmpty();
    }
}