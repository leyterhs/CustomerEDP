package com.customeredp.controller;

import com.customeredp.model.Delivery;
import com.customeredp.model.Member;
import com.customeredp.service.DeliveryService;
import com.customeredp.service.EngagementService;
import com.customeredp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@Tag(name = "Delivery Controller", description = "CRUD operations for deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final MemberService memberService;
    private final EngagementService engagementService;

    private Member getAuthenticatedMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return memberService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    @PostMapping
    @Operation(summary = "Create a new delivery")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        return ResponseEntity.ok(deliveryService.createDelivery(delivery));
    }

    @GetMapping
    @Operation(summary = "Get all deliveries for the current user")
    public ResponseEntity<List<Delivery>> getMyDeliveries() {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(deliveryService.getDeliveriesByMember(currentUser));
    }

    @GetMapping("/engagement/{engagementId}")
    @Operation(summary = "Get all deliveries for an engagement")
    public ResponseEntity<List<Delivery>> getDeliveriesByEngagement(@PathVariable Long engagementId) {
        var engagement = engagementService.getEngagementById(engagementId);
        return ResponseEntity.ok(deliveryService.getDeliveriesByEngagement(engagement));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a delivery by ID")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a delivery")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, @RequestBody Delivery delivery) {
        return ResponseEntity.ok(deliveryService.updateDelivery(id, delivery));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a delivery")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}