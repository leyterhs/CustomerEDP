package com.customeredp.repository;

import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findByCreatedBy(Member member);
}