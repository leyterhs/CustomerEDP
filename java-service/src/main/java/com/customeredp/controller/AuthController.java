package com.customeredp.controller;

import com.customeredp.model.Member;
import com.customeredp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");

        try {
            Member member = memberService.registerMember(username, email, password);
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        // Αυτό το endpoint θα το ολοκληρώσουμε όταν φτιάξουμε το JWT
        return ResponseEntity.ok(Map.of("message", "Login endpoint - JWT coming soon"));
    }
}