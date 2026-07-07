package com.customeredp.service;

import com.customeredp.model.Member;
import com.customeredp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // ✅ Προσθήκη για κρυπτογράφηση

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    /**
     * Εγγραφή νέου χρήστη (MEMBER).
     */
    public Member registerMember(String username, String email, String rawPassword) {
        // Έλεγχος αν το username υπάρχει ήδη
        if (memberRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        // Έλεγχος αν το email υπάρχει ήδη
        if (memberRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(rawPassword)); // Κρυπτογράφηση
        member.setRole("MEMBER"); // Default role

        return memberRepository.save(member);
    }
}