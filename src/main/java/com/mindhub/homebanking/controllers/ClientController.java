package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//controlador: escucha y envia respuestas http
@RestController
@CrossOrigin(origins = "*")
@RequestMapping( "/api/clients" )
public class ClientController {
    //servlets: responden una peticion en especifico
    //inyeccion de dependencias
    @Autowired
    private ClientRepository clientrepository;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients(){
        List<Client> client = clientrepository.findAll();

        return new ResponseEntity<>(client.stream().map(ClientDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<ClientDTO> getClientById( @PathVariable Long id ){
        Client client = clientrepository.findById(id).orElse(null);

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

}
