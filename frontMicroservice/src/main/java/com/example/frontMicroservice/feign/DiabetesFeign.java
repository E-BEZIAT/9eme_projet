package com.example.frontMicroservice.feign;

import com.example.frontMicroservice.response.DiabetesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "diabeteMicroservice", url = "http://localhost:8084")
public interface DiabetesFeign {

    @GetMapping("/diabetes/{patientId}")
    DiabetesDTO getRiskOfDiabetesByPatientId(@PathVariable("patientId") int patientId);
}
