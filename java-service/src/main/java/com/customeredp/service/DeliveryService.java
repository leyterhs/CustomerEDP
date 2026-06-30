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

    public List<Delivery> getDeliveriesByEngagement(Engagement engagement) {
        return deliveryRepository.findByEngagement(engagement);
    }

    public List<Delivery> getDeliveriesByMember(Member member) {
        return deliveryRepository.findByAssignedTo(member);
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
    }

    public Delivery updateDelivery(Long id, Delivery updated) {
        Delivery existing = getDeliveryById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setAssignedTo(updated.getAssignedTo());
        existing.setPriority(updated.getPriority());
        existing.setStatus(updated.getStatus());
        existing.setDueDate(updated.getDueDate());
        return deliveryRepository.save(existing);
    }

    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}