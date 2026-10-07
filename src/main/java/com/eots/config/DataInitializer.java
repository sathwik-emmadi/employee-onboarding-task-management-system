package com.eots.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eots.entity.Employee;
import com.eots.enums.Role;
import com.eots.repository.EmployeeRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeDefaultHR(
            EmployeeRepository employeeRepository,
            BCryptPasswordEncoder passwordEncoder) {

        return args -> {

            String hrEmail = "hr@eots.com";

            if (!employeeRepository.existsByEmail(hrEmail)) {

                Employee hr = new Employee();

                hr.setFullName("EOTS HR");
                hr.setEmail(hrEmail);

                hr.setPassword(
                        passwordEncoder.encode("HR@123456")
                );

                hr.setRole(Role.HR);

                employeeRepository.save(hr);

                System.out.println(
                        "Default HR account created."
                );
            }
        };
    }
}