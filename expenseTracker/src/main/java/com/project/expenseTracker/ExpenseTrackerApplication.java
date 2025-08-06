package com.project.expenseTracker;

import com.project.expenseTracker.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class ExpenseTrackerApplication {


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExpenseTrackerApplication.class, args);


    }
}
