package com.customeredp.controller;

import com.customeredp.model.Engagement;
import com.customeredp.model.Member;
import com.customeredp.service.EngagementService;
import com.customeredp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/engagements")
@RequiredArgsConstructor
public class EngagementController {

    private final EngagementService engagementService;
    private final MemberService memberService;

    private Member getAuthenticatedMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return memberService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ResponseEntity<Engagement> createEngagement(@RequestBody Engagement engagement) {
        Member currentUser = getAuthenticatedMember();
        Engagement created = engagementService.createEngagement(engagement, currentUser);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Engagement>> getMyEngagements() {
        Member currentUser = getAuthenticatedMember();
        List<Engagement> engagements = engagementService.getEngagementsByMember(currentUser);
        return ResponseEntity.ok(engagements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Engagement> getEngagement(@PathVariable Long id) {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(engagementService.getEngagementById(id, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Engagement> updateEngagement(@PathVariable Long id, @RequestBody Engagement engagement) {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(engagementService.updateEngagement(id, engagement, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngagement(@PathVariable Long id) {
        Member currentUser = getAuthenticatedMember();
        engagementService.deleteEngagement(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}