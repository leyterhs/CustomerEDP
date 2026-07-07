package com.customeredp.service;

import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import com.customeredp.repository.EngagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EngagementService {

    private final EngagementRepository engagementRepository;

    public Engagement createEngagement(Engagement engagement, Member createdBy) {
        engagement.setCreatedBy(createdBy);
        return engagementRepository.save(engagement);
    }

    // ✅ ADMIN βλέπει όλα, άλλοι μόνο τα δικά τους
    public List<Engagement> getEngagementsByMember(Member member) {
        if ("ADMIN".equalsIgnoreCase(member.getRole())) {
            return engagementRepository.findAll();
        }
        return engagementRepository.findByCreatedBy(member);
    }

    public Engagement getEngagementById(Long id) {
        return engagementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Engagement not found"));
    }

    // ✅ ADMIN μπορεί να δει οποιοδήποτε engagement
    public Engagement getEngagementById(Long id, Member currentUser) {
        Engagement engagement = getEngagementById(id);
        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return engagement;
        }
        if (!engagement.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to view this engagement");
        }
        return engagement;
    }

    public Engagement updateEngagement(Long id, Engagement updated, Member currentUser) {
        Engagement existing = getEngagementById(id, currentUser);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        // ✅ Χρησιμοποιούμε budget αντί για priority, deadline αντί για dueDate
        existing.setBudget(updated.getBudget());
        existing.setDeadline(updated.getDeadline());
        existing.setClient(updated.getClient());
        return engagementRepository.save(existing);
    }

    public void deleteEngagement(Long id, Member currentUser) {
        Engagement engagement = getEngagementById(id, currentUser);
        engagementRepository.delete(engagement);
    }
}