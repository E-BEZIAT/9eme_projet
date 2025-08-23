package com.example.frontMicroservice.feign;


import com.example.frontMicroservice.response.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "medecinMicroservice", url = "http://localhost:8082")
public interface MedecinFeign {

    @GetMapping("/note/patient/{patientId}")
    List<NoteDTO> getNotesByPatientId(@PathVariable("patientId") int patientId);
}
