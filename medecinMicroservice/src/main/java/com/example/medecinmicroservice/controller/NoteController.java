package com.example.medecinmicroservice.controller;

import com.example.medecinmicroservice.module.reponse.NoteDTO;
import com.example.medecinmicroservice.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
