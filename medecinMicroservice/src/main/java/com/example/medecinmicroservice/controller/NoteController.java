package com.example.medecinmicroservice.controller;

import com.example.medecinmicroservice.module.parameter.NoteParameter;
import com.example.medecinmicroservice.module.reponse.NoteDTO;
import com.example.medecinmicroservice.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/patient/{patientId}")
    public List<NoteDTO> getNotesByPatientId(@PathVariable("patientId") int patientId) {
        return noteService.getNotesByPatientId(patientId);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNote(@RequestBody NoteDTO noteDTO) {
        noteService.createNote(noteDTO);
        return ResponseEntity.ok("Note créée avec succès");
    }
}
