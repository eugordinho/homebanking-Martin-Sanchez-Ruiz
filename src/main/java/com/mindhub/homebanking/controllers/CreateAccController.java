package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients/current")
public class CreateAccController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RandomNumber randomNumber;

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(email);

        return ResponseEntity.ok(accountService.getAllAccountsDTOByAccountHolder(client));
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);

        List<Account> accounts = accountService.getAllAccountsByAccountHolder(client);

        if(accounts.size() < 3){
            String accountNumber;
            do {
                accountNumber = "VIN" + String.format("%08d", RandomNumber.getRandomNumber(0, 100000000));
            } while (accountService.getAccountByNumber(accountNumber) != null);


            Account account = new Account(accountNumber, 0, LocalDate.now());
            client.addAccount(account);
            clientService.saveClient(client);
            accountService.saveAccount(account);
            return new ResponseEntity<> ("Account created!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<> ("You reached the maximum number of accounts allowed (3)", HttpStatus.FORBIDDEN);
        }
    }

}
