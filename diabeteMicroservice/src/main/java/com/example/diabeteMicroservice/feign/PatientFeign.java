package com.example.diabeteMicroservice.feign;

import com.example.diabeteMicroservice.model.response.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "patientMicroservice", url = "http://localhost:8081")
public interface PatientFeign {

    @GetMapping("/patient/{id}")
    PatientDTO getPatientById(@PathVariable("id") int id);
}
