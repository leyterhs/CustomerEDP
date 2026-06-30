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

    public List<Client> getClientsByMember(Member member) {
        return clientRepository.findByCreatedBy(member);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    public Client updateClient(Long id, Client updatedClient) {
        Client existing = getClientById(id);
        existing.setName(updatedClient.getName());
        existing.setEmail(updatedClient.getEmail());
        existing.setPhone(updatedClient.getPhone());
        existing.setCompany(updatedClient.getCompany());
        return clientRepository.save(existing);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}