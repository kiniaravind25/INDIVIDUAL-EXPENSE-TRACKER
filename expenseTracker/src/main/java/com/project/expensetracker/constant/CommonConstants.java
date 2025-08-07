package com.project.expensetracker.constant;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.project.expensetracker.constant.ExpenseCategory.*;

@Slf4j
public class CommonConstants {

    private CommonConstants(){
        log.info("CommonConstants class");
    }
    public static final String CATEGORY = Arrays.asList(FOOD, TRAVEL, GROCERIES, ENTERTAINMENT, HEALTH).toString();
    public static final String USER_NOT_FOUND = "user not found";
    public static final String EXPENSE_NOT_FOUND = "Expense not found";
}
