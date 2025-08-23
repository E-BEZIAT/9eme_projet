package com.example.frontMicroservice.parameter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
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
