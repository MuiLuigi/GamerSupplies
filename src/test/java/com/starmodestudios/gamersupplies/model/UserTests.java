package com.starmodestudios.gamersupplies.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {

    @Test
    public void testUserEntityMapping() {
        User user = new User("testuser", "hashedPassword", "ROLE_CUSTOMER");

        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getPassword()).isEqualTo("hashedPassword");
        assertThat(user.getRole()).isEqualTo("ROLE_CUSTOMER");
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    public void testUserDetailsImplementation() {
        User user = new User("admin", "secure", "ROLE_ADMIN");

        assertThat(user.isAccountNonExpired()).isTrue();
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isCredentialsNonExpired()).isTrue();
        assertThat(user.isEnabled()).isTrue();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertThat(authorities).hasSize(1);
        assertThat(authorities.iterator().next().getAuthority()).isEqualTo("ROLE_ADMIN");
    }
}
