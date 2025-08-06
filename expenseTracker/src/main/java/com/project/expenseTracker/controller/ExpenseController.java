package com.project.expenseTracker.controller;

import com.project.expenseTracker.dto.ExpenseDto;
import com.project.expenseTracker.exception.CategoryNotFoundException;
import com.project.expenseTracker.exception.InvalidAmountException;
import com.project.expenseTracker.exception.TitleCannotBeNullException;
import com.project.expenseTracker.exception.UserNotFoundException;
import com.project.expenseTracker.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> addExpense(@RequestBody ExpenseDto expense, @RequestParam String username)  {
       try{
        var finalResponse = expenseService.addTheExpense(expense,username);
        return new ResponseEntity<>(finalResponse, HttpStatus.CREATED);
    }catch (CategoryNotFoundException | TitleCannotBeNullException | InvalidAmountException | UserNotFoundException e){
           log.error("Invalid Input From the User :{}",e.getMessage());
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getUserName(@RequestParam String username){
      try{
          var result = expenseService.getAllExpensesByUser(username);
          return new ResponseEntity<>(result, HttpStatus.CREATED);
      }catch (UsernameNotFoundException e){
          log.error("User Not Found :{}",e.getMessage());
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

      }


    }
}
