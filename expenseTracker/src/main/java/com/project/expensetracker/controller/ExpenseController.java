package com.project.expensetracker.controller;

import com.project.expensetracker.dto.ExpenseDto;
import com.project.expensetracker.exception.CategoryNotFoundException;
import com.project.expensetracker.exception.InvalidAmountException;
import com.project.expensetracker.exception.TitleCannotBeNullException;
import com.project.expensetracker.exception.UserNotFoundException;
import com.project.expensetracker.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/expenseTracker")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addExpense(@RequestBody ExpenseDto expense, @RequestParam String username) {
        try {
            var finalResponse = expenseService.addTheExpense(expense, username);
            return new ResponseEntity<>(finalResponse, HttpStatus.CREATED);
        } catch (CategoryNotFoundException | TitleCannotBeNullException | InvalidAmountException |
                 UserNotFoundException e) {
            log.error("Invalid Input From the User :{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getUserName(@RequestParam String username) {
        try {
            var result = expenseService.getAllExpensesByUser(username);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            log.error("User Not Found :{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable Long expenseId, @RequestParam String username) {
        String result = expenseService.deleteExpenseById(expenseId, username);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<String> updateExpense(
            @PathVariable Long expenseId,
            @RequestBody ExpenseDto updatedExpense,
            @RequestParam String username) throws TitleCannotBeNullException {
        String result = expenseService.updateExpense(expenseId, updatedExpense, username);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calculateTotalExpenses(@RequestParam String username) {
        BigDecimal total = expenseService.calculateTotalExpenses(username);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ExpenseDto>> filterExpenses(
            @RequestParam String username,
            @RequestParam(required = false) String category,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate){
        try {
            var result = expenseService.filterExpenseByDate(username, category, startDate, endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("Data not found :{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/searchByMonth")
    public ResponseEntity<List<ExpenseDto>> searchExpensesByMonth(@RequestParam String username, @RequestParam Month month) {
        try {
            var result = expenseService.getMonthlyExpenses(username, month);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("User not found: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
}
