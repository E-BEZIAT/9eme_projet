package com.example.medecinmicroservice.repository;

import com.example.medecinmicroservice.module.Note;
import com.example.medecinmicroservice.module.reponse.NoteDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<NoteDTO> findByPatientId(int patientId);
}
