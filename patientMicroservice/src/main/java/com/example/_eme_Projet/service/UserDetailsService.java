package com.example._eme_Projet.service;

import com.example._eme_Projet.model.User;
import com.example._eme_Projet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Donne le rôle "USER" lors de la connexion au site, ce qui permet de pouvoir naviguer dessus
     *
     * @param username le nom d'utilisateur utilisé pour se connecter
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User profile = userRepository.findByUsername(username);
        if (profile == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(profile.getUsername())
                .password(profile.getPassword())
                .roles("USER")
                .build();
    }
}
