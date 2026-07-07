package com.customeredp.repository;

import com.customeredp.model.Delivery;
import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    // ✅ Νέα μέθοδος για να βρίσκει deliveries με βάση τον δημιουργό του client
    List<Delivery> findByEngagement_Client_CreatedBy(Member member);

    List<Delivery> findByEngagement(Engagement engagement);
}