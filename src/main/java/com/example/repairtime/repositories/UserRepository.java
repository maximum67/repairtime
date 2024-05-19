package com.example.repairtime.repositories;

import com.example.repairtime.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByName(String name);

    Optional<User> getUserByName(String name);

}
