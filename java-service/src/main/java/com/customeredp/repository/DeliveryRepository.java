package com.customeredp.repository;

import com.customeredp.model.Delivery;
import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByEngagement(Engagement engagement);
    List<Delivery> findByAssignedTo(Member member);
}