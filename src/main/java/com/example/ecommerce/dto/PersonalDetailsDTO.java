package com.example.ecommerce.dto;

import com.example.ecommerce.entity.PersonalDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public record PersonalDetailsDTO(
        @JsonView(Views.Public.class) String firstname,
        @JsonView(Views.Public.class) String lastname,
        @JsonView(Views.Extended.class) String phoneNumber
) {
    public static PersonalDetailsDTO fromEntity(PersonalDetails personalDetails) {
        return new PersonalDetailsDTO(
                personalDetails.getFirstname(),
                personalDetails.getLastname(),
                personalDetails.getPhoneNumber()
        );
    }

    @JsonProperty("nameAndSurname")
    @JsonView(Views.Public.class)
    public String getNameAndSurname() {
        return String.join(" ", firstname, lastname);
    }

    public PersonalDetails toEntity() {
        return new PersonalDetails(
                this.firstname,
                this.lastname,
                this.phoneNumber
        );
    }
}
