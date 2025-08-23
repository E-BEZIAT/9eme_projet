package com.example._eme_Projet.model.parameter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@ToString
@Setter
@Getter
public class PatientParameter {

    private int id;
    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate dateOfBirth;
    @NotBlank
    private String gender;
    private String address;
    private String phoneNumber;
}
