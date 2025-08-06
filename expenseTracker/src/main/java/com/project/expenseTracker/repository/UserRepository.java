package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
        boolean existsByEmail(String email);
}
