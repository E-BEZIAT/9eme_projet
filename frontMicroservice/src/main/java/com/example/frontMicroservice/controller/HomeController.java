package com.example.frontMicroservice.controller;

import com.example.frontMicroservice.feign.PatientFeign;
import com.example.frontMicroservice.response.PatientDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PatientFeign patientFeign;

    public HomeController(PatientFeign patientFeign) {
        this.patientFeign = patientFeign;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        session.setAttribute("username", username);
        List<PatientDTO> patients = patientFeign.getPatient();
        model.addAttribute("patients", patients);
        return "home";
    }
}
