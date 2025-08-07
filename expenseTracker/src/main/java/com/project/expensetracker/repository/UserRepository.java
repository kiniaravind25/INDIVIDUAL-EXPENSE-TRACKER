package com.project.expensetracker.repository;

import com.project.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
        boolean existsByEmail(String email);
        Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

}
