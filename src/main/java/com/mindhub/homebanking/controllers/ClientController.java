package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//controlador: escucha y envia respuestas http
@RestController
@RequestMapping( "/api/clients" )
public class ClientController {
    //servlets: responden una peticion en especifico
    //inyeccion de dependencias
    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAllClients(){
        return new ResponseEntity<>(clientService.getAllClientsDTO(), HttpStatus.OK);
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<ClientDTO> getClientById( @PathVariable Long id ){
        Client client = clientService.getClientById(id);

        if(client == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        ClientDTO clientDTO = new ClientDTO(client);

        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String getClients(){
        return "Hello Clients!";
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);
        return ResponseEntity.ok(new ClientDTO(client));
    }
}
