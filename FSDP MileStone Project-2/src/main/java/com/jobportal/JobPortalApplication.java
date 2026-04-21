package com.jobportal;

import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JobPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobPortalApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByEmail("admin@jobportal.com").isEmpty()) {
                repo.save(User.builder()
                        .name("System Admin")
                        .email("admin@jobportal.com")
                        .password(encoder.encode("admin123"))
                        .role("ROLE_ADMIN")
                        .build());
            }
        };
    }
}

