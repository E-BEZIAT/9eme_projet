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
    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;
    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Le genre est obligatoire")
    private String gender;
    private String address;
    private String phoneNumber;
}
