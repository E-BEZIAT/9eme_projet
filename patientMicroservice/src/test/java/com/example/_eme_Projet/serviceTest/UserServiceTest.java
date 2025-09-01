package com.example._eme_Projet.serviceTest;

import com.example._eme_Projet.model.parameter.UserParameter;
import com.example._eme_Projet.repository.UserRepository;
import com.example._eme_Projet.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void createUserTest() {
        UserParameter userParameter = new UserParameter();
        userParameter.setUsername("username");
        userParameter.setPassword("123");
        userParameter.setEmail("email@email.com");

        when(userRepository.findByUsername("username")).thenReturn(null);
        when(passwordEncoder.encode("123")).thenReturn("encodedPassword");

        userService.createUser(userParameter);

        verify(userRepository, times(1)).findByUsername("username");
        verify(passwordEncoder, times(1)).encode("123");
        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals("username") &&
                        user.getPassword().equals("encodedPassword") &&
                        user.getEmail().equals("email@email.com")
        ));
    }
}
