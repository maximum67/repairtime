package com.example.repairtime.services;

import com.example.repairtime.models.Role;
import com.example.repairtime.models.User;
import com.example.repairtime.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    public boolean createNewUser(User user) {
        if (userRepository.getUserByName(user.getName()).isPresent()) {
            return false;
        } else {
            if (userRepository.findAll().isEmpty()) {
                user.getRoles().add(Role.ROLE_ADMIN);
            } else {
                user.getRoles().add(Role.ROLE_USER);
            }
            userRepository.save(user);
            return true;
        }
    }

    public List<User> getUsersList() {
        if (userRepository.findAll().isEmpty()) {
            return new ArrayList<>();
        } else {
            return userRepository.findAll();
        }
    }

    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void changeRole(User user, Map<String, String> strings) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String str : strings.keySet()) {
            if (roles.contains(str)) {
                user.getRoles().add(Role.valueOf(str));
            }
            userRepository.save(user);
        }
    }

    public User getByPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
