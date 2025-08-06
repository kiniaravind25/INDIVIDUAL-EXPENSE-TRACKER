package com.project.expenseTracker.config;



import com.project.expenseTracker.model.Role;
import com.project.expenseTracker.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role("ROLE_USER"));
                System.out.println("Inserted ROLE_USER");
            }

            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN"));
                System.out.println("Inserted ROLE_ADMIN");
            }
        };
    }
}
