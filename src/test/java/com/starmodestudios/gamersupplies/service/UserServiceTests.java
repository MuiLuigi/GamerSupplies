package com.starmodestudios.gamersupplies.service;

import com.starmodestudios.gamersupplies.model.User;
import com.starmodestudios.gamersupplies.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("rawpassword");
    }

    @Test
    public void testRegisterUser() {
        when(passwordEncoder.encode("rawpassword")).thenReturn("encodedpassword");
        
        User savedUserMock = new User("testuser", "encodedpassword", "ROLE_CUSTOMER");
        savedUserMock.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(savedUserMock);

        User registeredUser = userService.registerUser(user);

        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getPassword()).isEqualTo("encodedpassword");
        assertThat(registeredUser.getRole()).isEqualTo("ROLE_CUSTOMER");
        assertThat(registeredUser.isEnabled()).isTrue();
        
        verify(passwordEncoder, times(1)).encode("rawpassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLoadUserByUsernameFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));

        org.springframework.security.core.userdetails.UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("unknown");
        });
    }
}
