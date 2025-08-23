package com.example._eme_Projet.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientDTO {

    private Integer id;
    private String lastName;
    private String firstName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;

    public PatientDTO(
            int id,
            String lastName,
            String firstName,
            LocalDate dateOfBirth,
            String gender,
            String address,
            String phoneNumber
    ){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public PatientDTO() {}
}
