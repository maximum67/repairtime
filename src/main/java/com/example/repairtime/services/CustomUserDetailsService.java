package com.example.repairtime.services;

import com.example.repairtime.models.User;
import com.example.repairtime.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
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

      User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }else {
            return user;
        }
    }
}
