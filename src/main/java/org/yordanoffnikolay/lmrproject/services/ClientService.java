package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.dtos.ClientDto;
import org.yordanoffnikolay.lmrproject.models.Client;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    void createClient(ClientDto clientDto, User loggedUser);

    void updateClient(ClientDto clientDto, User loggedUser);

    void deleteClient(ClientDto clientDto, User loggedUser);

    List<Client> getAllClients(User loggedUser);

    Optional<Client> getClientByName(String name);
}
