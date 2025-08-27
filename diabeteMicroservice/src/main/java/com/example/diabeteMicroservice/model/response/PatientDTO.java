package com.example.diabeteMicroservice.model.response;

import lombok.ToString;

import java.time.LocalDate;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
