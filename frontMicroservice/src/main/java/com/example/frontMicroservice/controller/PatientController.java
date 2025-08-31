package com.example.frontMicroservice.controller;

import com.example.frontMicroservice.feign.DiabetesFeign;
import com.example.frontMicroservice.feign.MedecinFeign;
import com.example.frontMicroservice.feign.PatientFeign;
import com.example.frontMicroservice.parameter.PatientParameter;
import com.example.frontMicroservice.response.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientFeign patientFeign;
    private final MedecinFeign medecinFeign;
    private final DiabetesFeign diabetesFeign;

    public PatientController(PatientFeign patientFeign, MedecinFeign medecinFeign, DiabetesFeign diabetesFeign) {
        this.patientFeign = patientFeign;
        this.medecinFeign = medecinFeign;
        this.diabetesFeign = diabetesFeign;
    }

    @GetMapping("/{id}")
    public String patientForm(@PathVariable("id") int id, Model model, HttpSession session) {
        PatientDTO patientDTO = patientFeign.getPatientById(id);

        PatientParameter patientParameter = new PatientParameter();
        BeanUtils.copyProperties(patientDTO, patientParameter);
        model.addAttribute("patient", patientParameter);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        model.addAttribute("user", userDTO);
        session.setAttribute("username", username);

        return "patient/update";
    }

    @PostMapping("/update/{id}")
    public String patientUpdate(
            @PathVariable("id") int id,
            @Valid @ModelAttribute("patient") PatientParameter patientParameter,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "patient/update";
        }

        patientFeign.updatePatient(id, patientParameter);

        model.addAttribute("success", true);
        return "redirect:http://localhost:8080/patient/" + id;

    }

    @PostMapping("/delete/{id}")
    public String patientDelete(@PathVariable("id") int id, Model model) {
        patientFeign.deletePatient(id);
        model.addAttribute("success", true);
        return "redirect:/home";
    }

    @GetMapping("/create")
    public String createPatient(Model model) {
        model.addAttribute("patient", new PatientParameter());
        return "patient/create";
    }

    @PostMapping("/create")
    public String createPatient(
            @Valid @ModelAttribute("patient") PatientParameter patientParameter,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "patient/create";
        }

        patientFeign.createPatient(patientParameter);
        model.addAttribute("message", "Patient créé avec succès");
        return "redirect:/home";
    }

    @GetMapping("/notes/{id}")
    public String notesForm(@PathVariable("id") int id, Model model) {
        PatientDTO patientDTO = patientFeign.getPatientById(id);
        List<NoteDTO> notesDTO = medecinFeign.getNotesByPatientId(id);
        DiabetesDTO diabetesDTO = diabetesFeign.getRiskOfDiabetesByPatientId(id);

        PatientWithNotesDTO patientWithNotesDTO = new PatientWithNotesDTO();
        patientWithNotesDTO.setPatientDTO(patientDTO);
        patientWithNotesDTO.setNotes(notesDTO);

        model.addAttribute("patientWithNotesDTO", patientWithNotesDTO);
        model.addAttribute("diabetes", diabetesDTO);
        return "patient/notes";
    }


    @PostMapping("/notes/create/{id}")
    public String createNote(
            @PathVariable("id") int patientId,
            @Valid @ModelAttribute("note")  NoteDTO noteDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "patient/create";
        }

        noteDTO.setPatientId(patientId);
        noteDTO.setDateOfVisit(LocalDateTime.now());

        medecinFeign.createNote(noteDTO);

        model.addAttribute("message", "Note créée avec succès");

        return "redirect:http://localhost:8080/patient/notes/" + patientId;
    }

}
