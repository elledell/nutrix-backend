package com.nutrix.backend.repository;

import com.nutrix.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find a user by their name (e.g., for login later)
    Optional<User> findByName(String name);
}