//Name: Fahad Arif (N01729165)
//Course: Web Application Development (CPAN-228)

package com.starmodestudios.gamersupplies.service;

import com.starmodestudios.gamersupplies.model.User;
import com.starmodestudios.gamersupplies.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        String username = user.getUsername() == null ? "" : user.getUsername().trim();

        if (username.isBlank()) {
            throw new IllegalArgumentException("A username is required.");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("The username already exists.");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("The password must be at least 6 characters long.");
        }

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_CUSTOMER");
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username));
    }
}