package com.example.medecinmicroservice.serviceTest;

import com.example.medecinmicroservice.module.Note;
import com.example.medecinmicroservice.module.reponse.NoteDTO;
import com.example.medecinmicroservice.repository.NoteRepository;
import com.example.medecinmicroservice.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteService = new NoteService(noteRepository);
    }

    @Test
    public void createNoteTest() {
        LocalDateTime date = LocalDateTime.of(2025, 7, 29, 14, 50);

        Note note = new Note("1",2, date, "note 1");
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setId("1");
        noteDTO.setPatientId(2);
        noteDTO.setDateOfVisit(date);
        noteDTO.setNote("note 1");

        when(noteRepository.save(any(Note.class))).thenReturn(note);

        noteService.createNote(noteDTO);

        verify(noteRepository, times(1)).save(argThat(n ->
                n.getNote().equals("note 1") &&
                n.getDateOfVisit().equals(date)));
    }

    @Test
    public void getNoteByPatientIdTest() {
        int patientId = 1;
        LocalDateTime date = LocalDateTime.of(2025, 7, 29, 14, 50);

        NoteDTO note1 = new NoteDTO();
        note1.setPatientId(patientId);
        note1.setDateOfVisit(date);
        note1.setNote("Note 1");

        NoteDTO note2 = new NoteDTO();
        note2.setPatientId(patientId);
        note2.setDateOfVisit(date.plusDays(1));
        note2.setNote("Note 2");

        List<NoteDTO> notes = List.of(note1, note2);

        when(noteRepository.findByPatientId(patientId)).thenReturn(notes);

        List<NoteDTO> result = noteService.getNotesByPatientId(patientId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Note 1", result.get(0).getNote());
        assertEquals("Note 2", result.get(1).getNote());

        verify(noteRepository, times(1)).findByPatientId(patientId);
    }

}
