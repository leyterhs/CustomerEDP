package com.customeredp.service;

import com.customeredp.model.Client;
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

    public List<Engagement> getEngagementsByMember(Member member) {
        return engagementRepository.findByCreatedBy(member);
    }

    public List<Engagement> getEngagementsByClient(Client client) {
        return engagementRepository.findByClient(client);
    }

    public Engagement getEngagementById(Long id) {
        return engagementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Engagement not found with id: " + id));
    }

    public Engagement updateEngagement(Long id, Engagement updated) {
        Engagement existing = getEngagementById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setClient(updated.getClient());
        existing.setStatus(updated.getStatus());
        existing.setBudget(updated.getBudget());
        existing.setDeadline(updated.getDeadline());
        return engagementRepository.save(existing);
    }

    public void deleteEngagement(Long id) {
        engagementRepository.deleteById(id);
    }
}