package com.customeredp.controller;

import com.customeredp.model.Member;
import com.customeredp.repository.MemberRepository;
import com.customeredp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Controller", description = "User management for administrators")
public class AdminController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<Member>> getAllUsers() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Create a new user (by Admin)")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        String role = request.getOrDefault("role", "MEMBER");

        if (memberRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }
        if (memberRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        }

        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        member.setRole(Member.Role.valueOf(role.toUpperCase()));

        return ResponseEntity.ok(memberRepository.save(member));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by ID")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!memberRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        memberRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}