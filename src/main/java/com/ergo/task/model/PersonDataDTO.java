package com.ergo.task.model;

import com.ergo.task.enums.PersonGender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.function.Consumer;

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

    public static final class Builder {
        public String personalId;
        public String firstName;
        public String lastName;
        public PersonGender gender;
        public LocalDate dateOfBirth;
        public String phoneNumber;
        public String email;

        public Builder with(Consumer<Builder> builderConsumer) {
            builderConsumer.accept(this);
            return this;
        }

        public PersonDataDTO build() {
            return new PersonDataDTO(personalId, firstName, lastName, gender, dateOfBirth, phoneNumber, email);
        }
    }
}
