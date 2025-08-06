package com.project.expenseTracker.service;

import ch.qos.logback.core.util.StringUtil;
import com.project.expenseTracker.constant.CommonConstants;
import com.project.expenseTracker.dto.ExpenseDto;
import com.project.expenseTracker.exception.CategoryNotFoundException;
import com.project.expenseTracker.exception.InvalidAmountException;
import com.project.expenseTracker.exception.TitleCannotBeNullException;
import com.project.expenseTracker.exception.UserNotFoundException;
import com.project.expenseTracker.mapper.ExpenseMapper;
import com.project.expenseTracker.model.Expense;
import com.project.expenseTracker.model.User;
import com.project.expenseTracker.repository.ExpenseRepository;
import com.project.expenseTracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;


  public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository,ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.expenseMapper = expenseMapper;
    }


    public String addTheExpense(ExpenseDto expense,String username) throws TitleCannotBeNullException, InvalidAmountException, CategoryNotFoundException, UserNotFoundException {

      User user = userRepository.findByUsername(username).orElseThrow(() ->new UserNotFoundException("User Not Found"));

        if(StringUtil.isNullOrEmpty(expense.getTitle()) ){
          throw new TitleCannotBeNullException("Users Title is empty");

      }
        if(expense.getAmount().compareTo(BigDecimal.ZERO)<0){
            throw new InvalidAmountException("Amount cannot be 0");

        }
        if(expense.getCategory().contains(CommonConstants.CATEGORY)){
            throw new CategoryNotFoundException("Category is not valid");

        }

        Expense expenseToAdd = new Expense();
        expenseToAdd.setTitle(expense.getTitle());
        expenseToAdd.setAmount(expense.getAmount());
        expenseToAdd.setCategory(expense.getCategory());
        expenseToAdd.setExpenseDate(expense.getExpenseDate());
        expenseToAdd.setDescription(expense.getDescription());
        expenseToAdd.setUser(user);
        expenseRepository.save(expenseToAdd);
        log.info("Expense added successfully");

        return "User's expense added successfully";
    }

    public List<ExpenseDto> getAllExpensesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Expense> expenses = expenseRepository.findByUser(user);
        return expenseMapper.mapToResponse(expenses);
    }


}
