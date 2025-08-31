package com.example._eme_Projet.service;

import com.example._eme_Projet.model.User;
import com.example._eme_Projet.model.parameter.UserParameter;
import com.example._eme_Projet.model.response.UserDTO;
import com.example._eme_Projet.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserParameter userParameter) {
        User user = userRepository.findByUsername(userParameter.getUsername());

        if (user != null) {
            throw new IllegalArgumentException("L'utilisateur existe déjà : " + userParameter.getUsername());
        }

        String encodedPassword = passwordEncoder.encode(userParameter.getPassword());

        User newUser = new User(
                userParameter.getUsername(),
                encodedPassword,
                userParameter.getEmail()
        );

        userRepository.save(newUser);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


}
