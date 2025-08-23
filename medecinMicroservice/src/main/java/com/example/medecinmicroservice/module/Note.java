package com.example.medecinmicroservice.module;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Document(collection = "note")
public class Note {

    @Id
    private String id;

    private int patientId;
    @NotBlank(message = "La date de la note ne peut pas être vide")
    private LocalDateTime dateOfVisit;
    @NotBlank(message = "Le contenu de la note ne peut pas être vide")
    private String note;

    public Note(String id, int patientId, LocalDateTime dateOfVisit, String note) {
        this.id = id;
        this.patientId = patientId;
        this.dateOfVisit = dateOfVisit;
        this.note = note;
    }

    public Note(int patientId, LocalDateTime dateOfVisit, String note) {
        this.patientId = patientId;
        this.dateOfVisit = dateOfVisit;
        this.note = note;
    }
}
