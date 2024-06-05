package com.example.repairtime.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf().disable()
                .authorizeHttpRequests(auth -> auth.requestMatchers("/css/**", "/images/*","/auth/loginError", "/auth/registration","/auth/logout")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
//                .and()
                .formLogin((formLogin) -> formLogin.loginPage("/auth/login")
                                                   .defaultSuccessUrl("/select/mark")
                                                   .permitAll()
                                                   .failureHandler(authenticationFailureHandler()))
                .logout((logout)-> logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                                         .invalidateHttpSession(true)
                                         .clearAuthentication(true)
                                         .deleteCookies("JSESSIONID")
                                         .logoutSuccessUrl("/auth/login")
                                         .permitAll())
                .sessionManagement((session)->session.maximumSessions(1).maxSessionsPreventsLogin(false))
                .build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
