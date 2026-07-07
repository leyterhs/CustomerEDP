package com.customeredp.controller;

import com.customeredp.model.Member;
import com.customeredp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<List<Member>> getAllUsers() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Member member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        if (member.getEmail() != null && memberRepository.existsByEmail(member.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        // ✅ Χρησιμοποιούμε τον ρόλο που έστειλε το frontend, όχι hardcoded ADMIN
        if (member.getRole() == null || member.getRole().isEmpty()) {
            member.setRole("MEMBER"); // Default role
        }
        // Το frontend στέλνει "ADMIN" ή "MEMBER" – το χρησιμοποιούμε ως έχει

        Member saved = memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        memberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}