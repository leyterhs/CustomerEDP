package com.customeredp;

import com.customeredp.model.Member;
import com.customeredp.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CustomerEdpApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerEdpApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdmin(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (memberRepository.findByUsername("admin").isEmpty()) {
                Member admin = new Member();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole("ADMIN");
                admin.setEmail("admin@example.com");
                admin.setFullName("Administrator");
                memberRepository.save(admin);
                System.out.println("========================================");
                System.out.println("✅ Admin user created!");
                System.out.println("   Username: admin");
                System.out.println("   Password: admin");
                System.out.println("========================================");
            } else {
                System.out.println("✅ Admin user already exists");
            }
        };
    }
}