package com.example.repairtime.services;

import com.example.repairtime.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return userRepository.findByName(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

    }
}
