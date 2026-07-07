package com.customeredp.controller;

import com.customeredp.model.Delivery;
import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import com.customeredp.service.DeliveryService;
import com.customeredp.service.EngagementService;
import com.customeredp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final EngagementService engagementService;
    private final MemberService memberService;

    private Member getAuthenticatedMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return memberService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        return ResponseEntity.ok(deliveryService.createDelivery(delivery));
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> getMyDeliveries() {
        Member currentUser = getAuthenticatedMember();
        List<Delivery> deliveries = deliveryService.getDeliveriesByMember(currentUser);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/engagement/{engagementId}")
    public ResponseEntity<List<Delivery>> getDeliveriesByEngagement(@PathVariable Long engagementId) {
        Engagement engagement = engagementService.getEngagementById(engagementId);
        return ResponseEntity.ok(deliveryService.getDeliveriesByEngagement(engagement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(deliveryService.getDeliveryById(id, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, @RequestBody Delivery delivery) {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(deliveryService.updateDelivery(id, delivery, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        Member currentUser = getAuthenticatedMember();
        deliveryService.deleteDelivery(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}