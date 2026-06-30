package com.customeredp.repository;

import com.customeredp.model.Client;
import com.customeredp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Βρίσκει όλους τους πελάτες που ανήκουν σε ένα συγκεκριμένο μέλος
    List<Client> findByCreatedBy(Member member);
}