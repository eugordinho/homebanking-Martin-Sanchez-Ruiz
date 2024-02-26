package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping( "/api/accounts" )
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){
        List<Account> account = accountRepository.findAll();

        return new ResponseEntity<>(account.stream().map(AccountDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping( "/{id}")
    public ResponseEntity<AccountDTO> getAccountById( @PathVariable Long id ){
        Account account = accountRepository.findById(id).orElse(null);

        if(account == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        AccountDTO accountDTO = new AccountDTO(account);

        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

}
