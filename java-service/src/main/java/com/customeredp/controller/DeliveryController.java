package com.customeredp.controller;

import com.customeredp.model.Delivery;
import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import com.customeredp.service.DeliveryService;
import com.customeredp.service.EngagementService;
import com.customeredp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new delivery")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Map<String, Object> payload) {
        // Δημιουργούμε το Delivery από το payload
        Delivery delivery = new Delivery();
        delivery.setTitle((String) payload.get("title"));
        delivery.setDescription((String) payload.getOrDefault("description", ""));
        delivery.setPriority((String) payload.getOrDefault("priority", "MEDIUM"));
        delivery.setStatus((String) payload.getOrDefault("status", "PENDING"));
        
        // Χειριζόμαστε την ημερομηνία
        String dueDateStr = (String) payload.get("dueDate");
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            delivery.setDueDate(LocalDate.parse(dueDateStr));
        }
        
        // Χειριζόμαστε το engagement
        Map<String, Object> engagementMap = (Map<String, Object>) payload.get("engagement");
        if (engagementMap != null && engagementMap.containsKey("id")) {
            Long engagementId = Long.valueOf(engagementMap.get("id").toString());
            Engagement engagement = engagementService.getEngagementById(engagementId);
            delivery.setEngagement(engagement);
        } else {
            throw new RuntimeException("Engagement ID is required");
        }
        
        return ResponseEntity.ok(deliveryService.createDelivery(delivery));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all deliveries for the current user")
    public ResponseEntity<List<Delivery>> getMyDeliveries() {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(deliveryService.getDeliveriesByMember(currentUser));
    }

    @GetMapping("/engagement/{engagementId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all deliveries for an engagement")
    public ResponseEntity<List<Delivery>> getDeliveriesByEngagement(@PathVariable Long engagementId) {
        var engagement = engagementService.getEngagementById(engagementId);
        return ResponseEntity.ok(deliveryService.getDeliveriesByEngagement(engagement));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a delivery by ID")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update a delivery")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        // Βρίσκουμε το υπάρχον delivery
        Delivery existing = deliveryService.getDeliveryById(id);
        
        // Ενημερώνουμε τα πεδία
        if (payload.containsKey("title")) {
            existing.setTitle((String) payload.get("title"));
        }
        if (payload.containsKey("description")) {
            existing.setDescription((String) payload.get("description"));
        }
        if (payload.containsKey("priority")) {
            existing.setPriority((String) payload.get("priority"));
        }
        if (payload.containsKey("status")) {
            existing.setStatus((String) payload.get("status"));
        }
        if (payload.containsKey("dueDate")) {
            String dueDateStr = (String) payload.get("dueDate");
            if (dueDateStr != null && !dueDateStr.isEmpty()) {
                existing.setDueDate(LocalDate.parse(dueDateStr));
            }
        }
        if (payload.containsKey("engagement")) {
            Map<String, Object> engagementMap = (Map<String, Object>) payload.get("engagement");
            if (engagementMap != null && engagementMap.containsKey("id")) {
                Long engagementId = Long.valueOf(engagementMap.get("id").toString());
                Engagement engagement = engagementService.getEngagementById(engagementId);
                existing.setEngagement(engagement);
            }
        }
        
        return ResponseEntity.ok(deliveryService.updateDelivery(id, existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a delivery")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}