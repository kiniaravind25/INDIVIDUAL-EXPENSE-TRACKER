package com.project.expensetracker.config;



import com.project.expensetracker.model.Role;
import com.project.expensetracker.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role("ROLE_USER"));
                log.info("Inserted ROLE_USER");
            }

            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN"));
                log.info("Inserted ROLE_ADMIN");
            }
        };
    }
}
