package com.example.frontMicroservice.feign;


import com.example.frontMicroservice.response.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "medecinMicroservice", url = "http://localhost:8082")
public interface MedecinFeign {

    @GetMapping("/note/patient/{patientId}")
    List<NoteDTO> getNotesByPatientId(@PathVariable("patientId") int patientId);

    @PostMapping("/note/create")
    void createNote(@RequestBody NoteDTO noteDTO);
}
