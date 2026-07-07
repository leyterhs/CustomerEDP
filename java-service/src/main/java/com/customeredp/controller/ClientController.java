package com.customeredp.controller;

import com.customeredp.model.Client;
import com.customeredp.model.Member;
import com.customeredp.service.ClientService;
import com.customeredp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final MemberService memberService;

    private Member getAuthenticatedMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return memberService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Member currentUser = getAuthenticatedMember();
        Client created = clientService.createClient(client, currentUser);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getMyClients() {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(clientService.getClientsByMember(currentUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(clientService.getClientById(id, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(clientService.updateClient(id, client, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            Member currentUser = getAuthenticatedMember();
            clientService.deleteClient(id, currentUser);
            return ResponseEntity.ok().body("Client deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting client: " + e.getMessage());
        }
    }
}