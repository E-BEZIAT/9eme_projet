package com.example._eme_Projet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "last_name")
    @NotBlank
    private String lastName;
    @Column(name = "first_name")
    @NotBlank
    private String firstName;
    @Column(name = "date_of_birth")
    @NotNull
    private LocalDate dateOfBirth;
    @NotBlank
    private String gender;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;


    public Patient(
            String lastName,
            String firstName,
            LocalDate dateOfBirth,
            String gender,
            String address,
            String phoneNumber
    ) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient(
            int id,
            String lastName,
            String firstName,
            LocalDate dateOfBirth,
            String gender,
            String address,
            String phoneNumber
    ) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient() {
    }
}
