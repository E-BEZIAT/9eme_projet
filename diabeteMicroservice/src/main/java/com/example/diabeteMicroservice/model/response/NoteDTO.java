package com.example.diabeteMicroservice.model.response;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class NoteDTO {

    private String id;
    private int patientId;
    private LocalDateTime dateOfVisit;
    private String note;

    public NoteDTO() {}

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
