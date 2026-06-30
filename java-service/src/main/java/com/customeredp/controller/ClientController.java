package com.customeredp.controller;

import com.customeredp.model.Client;
import com.customeredp.model.Member;
import com.customeredp.service.ClientService;
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
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client Controller", description = "CRUD operations for clients")
public class ClientController {

    private final ClientService clientService;
    private final MemberService memberService;

    private Member getAuthenticatedMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return memberService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    @PostMapping
    @Operation(summary = "Create a new client")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Member currentUser = getAuthenticatedMember();
        Client created = clientService.createClient(client, currentUser);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @Operation(summary = "Get all clients for the current user")
    public ResponseEntity<List<Client>> getMyClients() {
        Member currentUser = getAuthenticatedMember();
        return ResponseEntity.ok(clientService.getClientsByMember(currentUser));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a client by ID")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a client")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}