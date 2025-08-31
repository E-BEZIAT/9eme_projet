package com.example.frontMicroservice.controller;

import com.example.frontMicroservice.feign.PatientFeign;
import com.example.frontMicroservice.parameter.UserParameter;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final PatientFeign patientFeign;

    public UserController(PatientFeign patientFeign) {
        this.patientFeign = patientFeign;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserParameter());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserParameter user, Model model) {
        patientFeign.createUser(user);
        model.addAttribute("message", "Utilisateur créé avec succès");
        return "login";
    }
}
