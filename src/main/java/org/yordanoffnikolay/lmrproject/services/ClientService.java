package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.dtos.ClientDto;
import org.yordanoffnikolay.lmrproject.models.Client;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    void createClient(ClientDto clientDto);

    void updateClient(ClientDto clientDto, long id);

    void deleteClient(long id);

    List<Client> getAllClients();

    Optional<Client> getClientById(long id);
}
