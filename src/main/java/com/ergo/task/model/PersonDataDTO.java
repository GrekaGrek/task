package com.ergo.task.model;

import com.ergo.task.enums.PersonGender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public record PersonDataDTO(
        @NotBlank
        @JsonProperty(required = true)
        @Size(min = 11, max = 11)
        String personalId,

        @NotBlank
        @JsonProperty(required = true)
        @Size(min = 3, max = 50)
        String firstName,

        @NotBlank
        @JsonProperty(required = true)
        @Size(min = 2, max = 50)
        String lastName,

        @NotNull
        @JsonProperty(required = true)
        PersonGender gender,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonProperty(required = true)
        LocalDate dateOfBirth,

        @NotBlank
        @JsonProperty(required = true)
        String phoneNumber,

        @NotBlank
        @JsonProperty(required = true)
        @Email(message = "Email is not valid", regexp =
                "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        String email
) {
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String personalId;
        private String firstName;
        private String lastName;
        private PersonGender gender;
        private LocalDate dateOfBirth;
        private String phoneNumber;
        private String email;

        private Builder() {
        }

        public Builder personalId(String personalId) {
            this.personalId = personalId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder gender(PersonGender gender) {
            this.gender = gender;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public PersonDataDTO build() {
            return new PersonDataDTO(personalId, firstName, lastName, gender, dateOfBirth, phoneNumber, email);
        }
    }
}
