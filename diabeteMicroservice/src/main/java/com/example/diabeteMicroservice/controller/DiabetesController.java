package com.example.diabeteMicroservice.controller;

import com.example.diabeteMicroservice.model.response.DiabetesDTO;
import com.example.diabeteMicroservice.service.DiabetesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diabetes")
public class DiabetesController {

    private final DiabetesService diabetesService;

    public DiabetesController(DiabetesService diabetesService) {
        this.diabetesService = diabetesService;
    }

    /** Ce controller retourne le risque de diabète pour un patient (identifié avec son id -> patientId)
     *
     * @param patientId
     * @return
     */
    @GetMapping("/{patientId}")
    public DiabetesDTO getRiskOfDiabetesByPatientId(@PathVariable("patientId") int patientId) {
        return diabetesService.diabetesReport(patientId);
    }
}
