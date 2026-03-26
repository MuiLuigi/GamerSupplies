package com.starmodestudios.gamersupplies.repository;

import com.starmodestudios.gamersupplies.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindByUsername() {
        User user = new User("testuser", "hashedpass", "ROLE_CUSTOMER");
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();

        Optional<User> foundUser = userRepository.findByUsername("testuser");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
        assertThat(foundUser.get().getRole()).isEqualTo("ROLE_CUSTOMER");
    }

    @Test
    public void testFindByUsernameNotFound() {
        Optional<User> foundUser = userRepository.findByUsername("nonexistent");
        assertThat(foundUser).isNotPresent();
    }
}
