package com.customeredp.repository;

import com.customeredp.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testSaveAndFindMember() {
        // 1. Δημιουργούμε έναν νέο χρήστη
        Member member = new Member();
        member.setUsername("testuser");
        member.setEmail("test@example.com");
        member.setPassword("password123");
        member.setRole(Member.Role.MEMBER);

        // 2. Τον αποθηκεύουμε στη ΒΔ
        Member savedMember = memberRepository.save(member);

        // 3. Τον αναζητούμε με το username
        Member foundMember = memberRepository.findByUsername("testuser").orElse(null);

        // 4. Ελέγχουμε ότι βρέθηκε και τα στοιχεία του είναι σωστά
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo("test@example.com");
        assertThat(foundMember.getRole()).isEqualTo(Member.Role.MEMBER);
    }
}