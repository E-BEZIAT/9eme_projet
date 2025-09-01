package com.example.frontMicroservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    /** retourne la page de connexion
     *
     * @param error error
     * @param model model from view
     * @return login
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Nom d'utilisateur ou mot de passe incorrect");
        }
        return "login";
    }
}
