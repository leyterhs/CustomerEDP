package com.customeredp.service;

import com.customeredp.model.Client;
import com.customeredp.model.Member;
import com.customeredp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(Client client, Member createdBy) {
        client.setCreatedBy(createdBy);
        return clientRepository.save(client);
    }

    // ✅ ADMIN βλέπει όλους τους clients (case-insensitive)
    public List<Client> getClientsByMember(Member member) {
        if ("ADMIN".equalsIgnoreCase(member.getRole())) {
            return clientRepository.findAll();
        }
        return clientRepository.findByCreatedBy(member);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    // ✅ ADMIN μπορεί να δει οποιονδήποτε client
    public Client getClientById(Long id, Member currentUser) {
        Client client = getClientById(id);
        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return client;
        }
        if (!client.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to view this client");
        }
        return client;
    }

    public Client updateClient(Long id, Client updatedClient, Member currentUser) {
        Client existing = getClientById(id, currentUser);
        existing.setName(updatedClient.getName());
        existing.setEmail(updatedClient.getEmail());
        existing.setPhone(updatedClient.getPhone());
        existing.setCompany(updatedClient.getCompany());
        return clientRepository.save(existing);
    }

    public void deleteClient(Long id, Member currentUser) {
        Client client = getClientById(id, currentUser);
        clientRepository.delete(client);
    }
}