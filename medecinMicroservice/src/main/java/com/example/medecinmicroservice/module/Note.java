package com.example.medecinmicroservice.module;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@ToString
@Document(collection = "note")
public class Note {

    @Id
    private String id;
    @NotNull
    private int patientId;
    @NotNull(message = "La date de la note ne peut pas être vide")
    private LocalDateTime dateOfVisit;
    @NotBlank(message = "Le contenu de la note ne peut pas être vide")
    private String note;

    public Note(String id, int patientId, LocalDateTime dateOfVisit, String note) {
        this.id = id;
        this.patientId = patientId;
        this.dateOfVisit = dateOfVisit;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(LocalDateTime dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
