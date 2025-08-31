package com.example._eme_Projet.controller;

import com.example._eme_Projet.model.Patient;
import com.example._eme_Projet.model.parameter.PatientParameter;
import com.example._eme_Projet.model.response.PatientDTO;
import com.example._eme_Projet.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

     //affiche le profil du patient
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable("id") Integer id) {
        Patient patient = patientService.readPatient(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        PatientDTO patientDTO = new PatientDTO();
        BeanUtils.copyProperties(patient, patientDTO);
        return ResponseEntity.ok(patientDTO);
    }

    //affiche la page de création du patient
    @Deprecated
    @GetMapping("/create")
    public ResponseEntity<PatientParameter> createPatientForm() {
        return ResponseEntity.ok(new PatientParameter());
    }

    //créer un patient
    @PostMapping("/create")
    public ResponseEntity<String> createPatient(@RequestBody PatientParameter patientParameter) {
        patientService.createNewPatient(patientParameter);
        return ResponseEntity.ok("Patient créé avec succès");
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updatePatient(
            @PathVariable("id") Integer id,
            @RequestBody PatientParameter patient
    ) {
        patientService.updatePatient(patient, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Integer id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
