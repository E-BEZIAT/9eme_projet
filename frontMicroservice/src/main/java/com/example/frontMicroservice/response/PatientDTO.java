package com.example.frontMicroservice.response;

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
}
