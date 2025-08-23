package com.example.frontMicroservice.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PatientWithNotesDTO {

    private PatientDTO patientDTO;
    private List<NoteDTO> notes;
}
