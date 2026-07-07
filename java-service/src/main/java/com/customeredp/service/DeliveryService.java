package com.customeredp.service;

import com.customeredp.model.Delivery;
import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import com.customeredp.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    // ✅ ADMIN βλέπει όλα τα deliveries, άλλοι μόνο τα δικά τους
    public List<Delivery> getDeliveriesByMember(Member member) {
        if ("ADMIN".equalsIgnoreCase(member.getRole())) {
            return deliveryRepository.findAll();
        }
        return deliveryRepository.findByEngagement_Client_CreatedBy(member);
    }

    public List<Delivery> getDeliveriesByEngagement(Engagement engagement) {
        return deliveryRepository.findByEngagement(engagement);
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

    // ✅ ADMIN μπορεί να δει οποιοδήποτε delivery
    public Delivery getDeliveryById(Long id, Member currentUser) {
        Delivery delivery = getDeliveryById(id);
        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return delivery;
        }
        // Έλεγχος ότι το delivery ανήκει στον χρήστη (μέσω engagement -> client -> createdBy)
        if (!delivery.getEngagement().getClient().getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to view this delivery");
        }
        return delivery;
    }

    public Delivery updateDelivery(Long id, Delivery updated, Member currentUser) {
        Delivery existing = getDeliveryById(id, currentUser);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        existing.setPriority(updated.getPriority());
        existing.setDueDate(updated.getDueDate());
        existing.setEngagement(updated.getEngagement());
        return deliveryRepository.save(existing);
    }

    public void deleteDelivery(Long id, Member currentUser) {
        Delivery delivery = getDeliveryById(id, currentUser);
        deliveryRepository.delete(delivery);
    }
}