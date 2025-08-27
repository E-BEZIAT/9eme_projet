package com.example.frontMicroservice.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiabetesDTO {

    private int patientId;
    private String lastName;
    private String firstName;
    private int age;
    private String riskLevel;

    public DiabetesDTO() {}
}
