package com.customeredp.controller;

import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import com.customeredp.service.ClientService;
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
@RequestMapping("/api/engagements")
@RequiredArgsConstructor
@Tag(name = "Engagement Controller", description = "CRUD operations for engagements")
public class EngagementController {

    private final EngagementService engagementService;
    private final MemberService memberService;
    private final ClientService clientService;

    private Member getAuthenticatedMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return memberService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    @PostMapping
    @Operation(summary = "Create a new engagement")
    public ResponseEntity<Engagement> createEngagement(@RequestBody Engagement engagement) {
        Member currentUser = getAuthenticatedMember();
        Engagement created = engagementService.createEngagement(engagement, currentUser);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @Operation(summary = "Get all engagements for the current user")
    public ResponseEntity<List<Engagement>> getMyEngagements() {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(engagementService.getEngagementsByMember(currentUser));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an engagement by ID")
    public ResponseEntity<Engagement> getEngagementById(@PathVariable Long id) {
        return ResponseEntity.ok(engagementService.getEngagementById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an engagement")
    public ResponseEntity<Engagement> updateEngagement(@PathVariable Long id, @RequestBody Engagement engagement) {
        return ResponseEntity.ok(engagementService.updateEngagement(id, engagement));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an engagement")
    public ResponseEntity<Void> deleteEngagement(@PathVariable Long id) {
        engagementService.deleteEngagement(id);
        return ResponseEntity.noContent().build();
    }
}