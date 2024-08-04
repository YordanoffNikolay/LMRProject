package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.dtos.ClientDto;
import org.yordanoffnikolay.lmrproject.models.Client;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    @Override
    public void createClient(ClientDto clientDto, User loggedUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateClient(ClientDto clientDto, User loggedUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteClient(ClientDto clientDto, User loggedUser) {
        throw new UnsupportedOperationException();

    }

    @Override
    public List<Client> getAllClients(User loggedUser) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Optional<Client> getClientByName(String name) {
        throw new UnsupportedOperationException();

    }
}
