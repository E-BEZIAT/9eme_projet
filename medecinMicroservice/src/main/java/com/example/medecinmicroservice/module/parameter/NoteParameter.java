package com.example.medecinmicroservice.module.parameter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class NoteParameter {

    @Id
    private String id;
    @NotNull
    private int patientId;
    @NotNull(message = "La date de la note ne peut pas être vide")
    private LocalDateTime dateOfVisite;
    @NotBlank(message = "Le contenu de la note ne peut pas être vide")
    private String note;

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

    public LocalDateTime getDateOfVisite() {
        return dateOfVisite;
    }

    public void setDateOfVisite(LocalDateTime dateOfVisite) {
        this.dateOfVisite = dateOfVisite;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // toString
    @Override
    public String toString() {
        return "NoteParameter{" +
                "id='" + id + '\'' +
                ", patientId=" + patientId +
                ", dateOfVisite=" + dateOfVisite +
                ", note='" + note + '\'' +
                '}';
    }
}
