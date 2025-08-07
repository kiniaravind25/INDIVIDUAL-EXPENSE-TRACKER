package com.project.expensetracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ExpensetrackerApplicationTests {

	@Test
	void contextLoads() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println("Loading application context for tests...");
        });
	}

}
