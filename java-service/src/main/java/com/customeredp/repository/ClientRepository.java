package com.customeredp.repository;

import com.customeredp.model.Client;
import com.customeredp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByCreatedBy(Member member);
}