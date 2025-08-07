package com.project.expenseTracker.constant;

import java.util.Arrays;

import static com.project.expenseTracker.constant.ExpenseCategory.*;

public class CommonConstants {

    public static final String CATEGORY = Arrays.asList(FOOD, TRAVEL, GROCERIES, ENTERTAINMENT, HEALTH).toString();
    public static final String USER_NOT_FOUND = "user not found";
    public static final String EXPENSE_NOT_FOUND = "Expense not found";
}
