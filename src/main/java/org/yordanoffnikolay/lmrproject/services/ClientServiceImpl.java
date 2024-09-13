package org.yordanoffnikolay.lmrproject.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.ClientDto;
import org.yordanoffnikolay.lmrproject.exceptions.AuthorizationException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Category;
import org.yordanoffnikolay.lmrproject.models.Client;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.repositories.CategoryRepository;
import org.yordanoffnikolay.lmrproject.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.yordanoffnikolay.lmrproject.services.UserServiceImpl.UNAUTHORIZED;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    public ClientServiceImpl(ClientRepository clientRepository, CategoryRepository categoryRepository) {
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createClient(ClientDto clientDto) {
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setCategory(categoryRepository.findByCategoryName(clientDto.getCategory()));
        clientRepository.save(client);
    }

    @Override
    public void updateClient(ClientDto clientDto, long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        client.setName(clientDto.getName());
        client.setCategory(categoryRepository.findByCategoryName(clientDto.getCategory()));
        clientRepository.save(client);
    }

    @Override
    public void deleteClient(long id) {
        String authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!authorities.contains("ADMIN") && !authorities.contains("MANAGER")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }
}
