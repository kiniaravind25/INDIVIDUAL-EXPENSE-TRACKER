//package com.project.expensetracker.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.expensetracker.dto.ExpenseDto;
//import com.project.expensetracker.service.ExpenseService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@WebMvcTest(ExpenseController.class)
//class ExpenseControllerTest {
//
//    @Mock
//    private MockMvc mockMvc;
//
//    @Mock
//    private ExpenseService expenseService;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testGetAllExpenses() throws Exception {
//        Mockito.when(expenseService.getAllExpensesByUser(Mockito.anyString()))
//                .thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/expensetracker")
//                        .param("username", "testuser"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testAddExpense() throws Exception {
//        ExpenseDto dto = new ExpenseDto();
//        Mockito.when(expenseService.addTheExpense(Mockito.any(), Mockito.anyString()))
//                .thenReturn(String.valueOf(dto));
//
//        mockMvc.perform(post("/expensetracker/add")
//                        .param("username", "testuser")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk());
//    }
//}