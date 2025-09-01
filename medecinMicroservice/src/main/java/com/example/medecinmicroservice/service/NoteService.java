package com.example.medecinmicroservice.service;

import com.example.medecinmicroservice.module.Note;
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

    /** Méthode pour créer une note
     *
     * @param noteDTO body for create
     */
    public void createNote(NoteDTO noteDTO) {
        Note note = new Note(
                null,
                noteDTO.getPatientId(),
                noteDTO.getDateOfVisit(),
                noteDTO.getNote()
        );

        noteRepository.save(note);
    }

    /** Retourne les notes d'un patient grâce à son id
     *
     * @param patientId id of patient
     * @return notes
     */
    public List<NoteDTO> getNotesByPatientId(int patientId) {
        return noteRepository.findByPatientId(patientId);
    }
}
