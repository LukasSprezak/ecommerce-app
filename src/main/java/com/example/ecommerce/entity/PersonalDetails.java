package com.example.ecommerce.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PersonalDetails {

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Nullable
    private String phoneNumber;

    protected PersonalDetails() {}

    public PersonalDetails(@Nullable String firstname,  String lastname, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

    public static PersonalDetails empty() {
        return new PersonalDetails();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
