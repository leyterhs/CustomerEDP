package com.customeredp.service;

import com.customeredp.model.Member;
import com.customeredp.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    void testRegisterMember_Success() {
        // Given
        String username = "testuser";
        String email = "test@example.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";

        when(memberRepository.existsByUsername(username)).thenReturn(false);
        when(memberRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        Member savedMember = new Member();
        savedMember.setUsername(username);
        savedMember.setEmail(email);
        savedMember.setPassword(encodedPassword);
        savedMember.setRole(Member.Role.MEMBER);

        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        // When
        Member result = memberService.registerMember(username, email, rawPassword);

        // Then
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(Member.Role.MEMBER, result.getRole());
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void testRegisterMember_UsernameAlreadyExists() {
        // Given
        String username = "testuser";
        String email = "test@example.com";
        String rawPassword = "password123";

        when(memberRepository.existsByUsername(username)).thenReturn(true);

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            memberService.registerMember(username, email, rawPassword);
        });
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void testFindByUsername_Success() {
        // Given
        String username = "testuser";
        Member member = new Member();
        member.setUsername(username);

        when(memberRepository.findByUsername(username)).thenReturn(Optional.of(member));

        // When
        Optional<Member> result = memberService.findByUsername(username);

        // Then
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }
}