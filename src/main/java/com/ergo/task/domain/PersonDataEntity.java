package com.ergo.task.domain;

import com.ergo.task.domain.audit.Auditable;
import com.ergo.task.enums.PersonGender;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "persons")
public class PersonDataEntity extends Auditable {

    @Column(name = "personal_id", nullable = false)
    private String personalId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonGender gender;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "email", nullable = false)
    private String email;

    public String getPersonalId() {
        return personalId;
    }

    public PersonDataEntity setPersonalId(String personalId) {
        this.personalId = personalId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PersonDataEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PersonDataEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PersonGender getGender() {
        return gender;
    }

    public PersonDataEntity setGender(PersonGender gender) {
        this.gender = gender;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public PersonDataEntity setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PersonDataEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PersonDataEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonDataEntity that = (PersonDataEntity) o;
        return Objects.equals(personalId, that.personalId) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) && gender == that.gender &&
                Objects.equals(dateOfBirth, that.dateOfBirth) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), personalId, firstName, lastName, gender, dateOfBirth, phoneNumber, email);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PersonDataEntity.class.getSimpleName() + "[", "]")
                .add("personalId=" + personalId)
                .add("firstName=" + firstName)
                .add("lastName=" + lastName)
                .add("gender=" + gender)
                .add("dateOfBirth=" + dateOfBirth)
                .add("phoneNumber=" + phoneNumber)
                .add("email=" + email)
                .toString();
    }
}
