package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionRequestDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/clients/current")
public class TransactionController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransactionCredit (@RequestBody TransactionRequestDTO transactionRequestDTO){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);

        if(transactionRequestDTO.description().isBlank()){
            return new ResponseEntity<>("The description field cannot be empty", HttpStatus.FORBIDDEN);
        }
        if(transactionRequestDTO.amount() <= 0){
            return new ResponseEntity<>("The amount entered is not valid", HttpStatus.FORBIDDEN);
        }
        if(transactionRequestDTO.numberDebit().isBlank()){
            return new ResponseEntity<>("The origin account field cannot be empty", HttpStatus.FORBIDDEN);
        }
        if(transactionRequestDTO.numberCredit().isBlank()){
            return new ResponseEntity<>("The destination account field cannot be empty", HttpStatus.FORBIDDEN);
        }

        Boolean accountExist = accountService.getAccountByNumberAndAccountHolder(transactionRequestDTO.numberDebit(), client);

        if(!accountExist){
            return new ResponseEntity<>("The origin account is not valid", HttpStatus.FORBIDDEN);
        }


        Account accountCredit = accountService.getAccountByNumber(transactionRequestDTO.numberCredit());

        if(accountCredit == null){
            return new ResponseEntity<>("The account entered does not exist", HttpStatus.FORBIDDEN);
        }

        if(transactionRequestDTO.numberDebit().equals(transactionRequestDTO.numberCredit())){
            return new ResponseEntity<>("Invalid operation. The accounts entered are the same", HttpStatus.FORBIDDEN);
        }


        Account accountDebit = accountService.getAccountByNumber(transactionRequestDTO.numberDebit());

        if(accountDebit.getBalance() < transactionRequestDTO.amount()){
            return new ResponseEntity<>("Insufficient funds. Please indicate a valid amount", HttpStatus.FORBIDDEN);
        }

        accountDebit.setBalance(accountDebit.getBalance() - transactionRequestDTO.amount());
        accountCredit.setBalance(accountCredit.getBalance() + transactionRequestDTO.amount());

        Transaction transactionDebit = new Transaction(-(transactionRequestDTO.amount()), transactionRequestDTO.description(), LocalDateTime.now(), TransactionType.DEBIT);
        Transaction transactionCredit = new Transaction(transactionRequestDTO.amount(), transactionRequestDTO.description(), LocalDateTime.now(), TransactionType.CREDIT);
        accountDebit.addTransaction(transactionDebit);
        accountCredit.addTransaction(transactionCredit);
        clientService.saveClient(client);
        accountService.saveAccount(accountDebit);
        accountService.saveAccount(accountCredit);
        transactionService.saveTransaction(transactionDebit);
        transactionService.saveTransaction(transactionCredit);


        return new ResponseEntity<>("Transaction created", HttpStatus.CREATED);

    }
}
