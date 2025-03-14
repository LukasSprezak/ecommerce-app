package com.example.ecommerce.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private UUID uuid;

    @NotNull
    @ManyToOne
    private User user;

    @Nullable
    private String commentBy;

    @NotNull
    private String country;

    @Nullable
    private String voivodeship;

    @NotNull
    private String city;

    private String municipality;

    @NotNull
    private String street;

    @NotNull
    private String streetNumber;

    @NotNull
    private String streetLocalNumber;

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    @Nullable
    public String getCommentBy() {
        return commentBy;
    }

    public String getCountry() {
        return country;
    }

    @Nullable
    public String getVoivodeship() {
        return voivodeship;
    }

    public String getCity() {
        return city;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetLocalNumber() {
        return streetLocalNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCommentBy(@Nullable String commentBy) {
        this.commentBy = commentBy;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setVoivodeship(@Nullable String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setStreetLocalNumber(String streetLocalNumber) {
        this.streetLocalNumber = streetLocalNumber;
    }
}
