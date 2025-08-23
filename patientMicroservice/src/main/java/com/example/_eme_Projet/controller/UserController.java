package com.example._eme_Projet.controller;

import com.example._eme_Projet.model.parameter.UserParameter;
import com.example._eme_Projet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserParameter user) {
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    /**@GetMapping("/user/user")
    public String updateUserForm(Model model, @RequestHeader("userX") String username) {

        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé en session");
        }

        UserParameter userParam = userService.readUser(username);
        model.addAttribute("user", userParam);

        return "user/user";
    }**/

    /**@GetMapping("user/{username}")
    public ResponseEntity<UserParameter> getUserByUsername(@PathVariable("username") String username) {
        UserParameter user = userService.readUser(username);
        return ResponseEntity.ok(user);
    }**/

    @PostMapping("/user/delete")
    public void deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
    }


}
