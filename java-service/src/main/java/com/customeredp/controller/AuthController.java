package com.customeredp.controller;

import com.customeredp.dto.LoginRequest;
import com.customeredp.dto.RegisterRequest;
import com.customeredp.model.Member;
import com.customeredp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Authentication endpoints (register, login)")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with MEMBER role")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            Member member = memberService.registerMember(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
            );
            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "username", member.getUsername(),
                "role", member.getRole().name()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates a user (Basic Auth) - JWT coming soon")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Θα το αντικαταστήσουμε με JWT στο επόμενο βήμα
        return ResponseEntity.ok(Map.of(
            "message", "Login endpoint - JWT coming soon",
            "username", request.getUsername()
        ));
    }
}