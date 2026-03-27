package com.starmodestudios.gamersupplies.config;

import com.starmodestudios.gamersupplies.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    // @Lazy breaks the circular dependency:
    // SecurityConfig needs UserService → UserService needs PasswordEncoder → PasswordEncoder is in SecurityConfig
    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    // ── Password Encoder ────────────────────────────────────────────────────────
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ── Authentication Provider ─────────────────────────────────────────────────
    // Wires UserService (which implements UserDetailsService) and the PasswordEncoder
    // together so Spring Security knows how to look up and verify users from the database.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ── Security Filter Chain ───────────────────────────────────────────────────
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())

            .authorizeHttpRequests(auth -> auth

                // ── Public routes — no login required ──────────────────────────
                .requestMatchers(
                    "/", "/home", "/about", "/categories",
                    "/products",            // list view stays public
                    "/register", "/login",
                    "/css/**", "/js/**", "/images/**",
                    "/h2-console/**"
                ).permitAll()

                // ── ADMIN + STAFF — can add products ──────────────────────────
                .requestMatchers("/products/add").hasAnyRole("ADMIN", "STAFF")

                // ── ADMIN only — full admin area ───────────────────────────────
                .requestMatchers("/products/admin/**", "/products/edit/**", "/products/delete/**")
                    .hasRole("ADMIN")

                // ── Everything else requires login ────────────────────────────
                .anyRequest().authenticated()
            )

            // ── Custom login page ───────────────────────────────────────────────
            // Member 2 built /login — Spring Security will POST credentials to /login
            // and redirect to /products on success.
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/products", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // ── Logout ──────────────────────────────────────────────────────────
            // Member 2's navbar POSTs to /logout — this handles it and redirects to
            // /login?logout so the "Logged out successfully" message shows.
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // ── H2 console support (dev only) ────────────────────────────────────
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            );

        return http.build();
    }
}
