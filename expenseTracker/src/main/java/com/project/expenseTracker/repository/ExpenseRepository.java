package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Expense;
import com.project.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);

    Optional<Expense> findByIdAndUser(Long id, User user);

    @Query("SELECT e FROM Expense e WHERE e.user = :user " + "AND (:category IS NULL OR e.category = :category) " + "AND (:startDate IS NULL OR e.expenseDate >= :startDate) " + "AND (:endDate IS NULL OR e.expenseDate <= :endDate)")
    List<Expense> filterExpenses(@Param("user") User user, @Param("category") String category, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
