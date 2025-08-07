// expenseTracker/src/test/java/com/project/expenseTracker/service/ExpenseServiceTest.java
package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.ExpenseDto;
import com.project.expenseTracker.exception.InvalidAmountException;
import com.project.expenseTracker.exception.TitleCannotBeNullException;
import com.project.expenseTracker.exception.UserNotFoundException;
import com.project.expenseTracker.mapper.ExpenseMapper;
import com.project.expenseTracker.model.Expense;
import com.project.expenseTracker.model.User;
import com.project.expenseTracker.repository.ExpenseRepository;
import com.project.expenseTracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void testGetAllExpenses() {
        User user = new User();
        when(expenseRepository.findByUser(user)).thenReturn(Collections.emptyList());
        when(userRepository.findByUsername("testuser"))
                .thenReturn(java.util.Optional.of(user));
        when(expenseMapper.mapToResponse(anyList())).thenReturn(Collections.emptyList());


        assertNotNull(expenseService.getAllExpensesByUser("testuser"));
    }

    @Test
    void testCalculateTotalExpenses() {
        User user = new User();
        Expense expense1 = new Expense();
        expense1.setAmount(BigDecimal.valueOf(100));
        Expense expense2 = new Expense();
        expense2.setAmount(BigDecimal.valueOf(200));
        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        when(expenseRepository.findByUser(user)).thenReturn(List.of(expense1, expense2));

        BigDecimal total = expenseService.calculateTotalExpenses("testuser");

        assertEquals(BigDecimal.valueOf(300), total);
    }

    @Test
    void testAddTheExpense_Valid() throws Exception {
        User user = new User();
        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setTitle("Test Expense");
        expenseDto.setAmount(BigDecimal.valueOf(100));
        expenseDto.setCategory("FOOD");

        String result = expenseService.addTheExpense(expenseDto, "testuser");

        assertEquals("User's expense added successfully", result);
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testAddTheExpense_InvalidAmount() {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setTitle("Test Expense");
        expenseDto.setAmount(BigDecimal.valueOf(-100));
        expenseDto.setCategory("Food");

        assertThrows(UserNotFoundException.class, () ->
                expenseService.addTheExpense(expenseDto, "testuser")
        );
    }

    @Test
    void testDeleteExpenseById_Valid() {
        User user = new User();
        Expense expense = new Expense();
        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        when(expenseRepository.findByIdAndUser(1L, user)).thenReturn(java.util.Optional.of(expense));

        String result = expenseService.deleteExpenseById(1L, "testuser");

        assertEquals("Expense deleted successfully", result);
        verify(expenseRepository, times(1)).delete(expense);
    }

    @Test
    void testUpdateExpense_TitleNull() {
        ExpenseDto updatedExpense = new ExpenseDto();
        updatedExpense.setTitle(null);

        assertThrows(UsernameNotFoundException.class, () ->
                expenseService.updateExpense(1L, updatedExpense, "testuser")
        );
    }
}