package com.example.medecinmicroservice.service;

import com.example.medecinmicroservice.module.Note;
import com.example.medecinmicroservice.module.parameter.NoteParameter;
import com.example.medecinmicroservice.module.reponse.NoteDTO;
import com.example.medecinmicroservice.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void createNote(NoteDTO noteDTO) {
        Note note = new Note(
                noteDTO.getId(),
                noteDTO.getPatientId(),
                noteDTO.getDateOfVisit(),
                noteDTO.getNote()
        );

        noteRepository.save(note);
    }

    public List<NoteDTO> getNotesByPatientId(int patientId) {
        return noteRepository.findByPatientId(patientId);
    }
}
