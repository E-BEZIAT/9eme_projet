package com.example.frontMicroservice.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NoteDTO {

    private String id;
    private int patientId;
    private LocalDateTime dateOfVisit;
    private String note;

    public NoteDTO() {}
}
