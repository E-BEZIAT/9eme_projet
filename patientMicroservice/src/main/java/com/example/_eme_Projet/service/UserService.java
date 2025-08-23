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

    public void updateUser(UserParameter userParameter, String oldUsername) {
        User user = userRepository.findByUsername(oldUsername);

        if (user == null) {
            throw new IllegalArgumentException("Utilisateur introuvable");
        }

        String newUsername = userParameter.getUsername();
        if (newUsername != null && !newUsername.equals(oldUsername) && !newUsername.isBlank()) {

            if(userRepository.findByUsername(newUsername) != null) {
                throw new IllegalArgumentException("Ce nom d'utilisateur n'est pas disponible.");
            }

            user.setUsername(newUsername);
        }

        if(userParameter.getPassword() != null && !userParameter.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(userParameter.getPassword());
            user.setPassword(encodedPassword);
        }

        if(userParameter.getEmail() != null && !userParameter.getEmail().isBlank()) {
            user.setEmail(userParameter.getEmail());
        }

        userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public UserParameter readUser(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        UserParameter userParameter = new UserParameter();
        userParameter.setId(user.getId());
        userParameter.setUsername(user.getUsername());
        userParameter.setEmail(user.getEmail());

        return userParameter;
    }


}
