package com.customeredp.repository;

import com.customeredp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Βρίσκει ένα μέλος με βάση το username (χρησιμοποιείται για login)
    Optional<Member> findByUsername(String username);

    // Βρίσκει ένα μέλος με βάση το email
    Optional<Member> findByEmail(String email);

    // Ελέγχει αν υπάρχει ήδη μέλος με αυτό το username
    boolean existsByUsername(String username);

    // Ελέγχει αν υπάρχει ήδη μέλος με αυτό το email
    boolean existsByEmail(String email);
}