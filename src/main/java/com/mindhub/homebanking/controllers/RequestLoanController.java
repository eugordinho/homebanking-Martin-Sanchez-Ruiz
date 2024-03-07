package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
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
public class RequestLoanController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanService clientLoanService;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> requestLoan (@RequestBody LoanAplicationDTO loanAplicationDTO) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);

        if(loanAplicationDTO.amount() <= 0){
            return new ResponseEntity<>("The amount field cannot be empty", HttpStatus.FORBIDDEN);
        }

        if(loanAplicationDTO.payments() <= 0){
            return new ResponseEntity<>("The payments field cannot be empty", HttpStatus.FORBIDDEN);
        }

        if(loanAplicationDTO.numberAccount().isBlank()){
            return new ResponseEntity<>("The account field cannot be empty", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanService.getLoanById(loanAplicationDTO.id());

        if(loan == null){
            return new ResponseEntity<>("The selected loan does not exist", HttpStatus.FORBIDDEN);
        }


        if(loanAplicationDTO.amount() > loan.getMaxAmount()){
            return new ResponseEntity<>("The loan amount must be less than " + loan.getMaxAmount(), HttpStatus.FORBIDDEN);
        }

        if(!loan.getPayments().contains(loanAplicationDTO.payments())){
            return new ResponseEntity<>("The amount of payments must be between " + loan.getPayments(), HttpStatus.FORBIDDEN);
        }

        Boolean accountExist = accountService.getAccountByNumberAndAccountHolder(loanAplicationDTO.numberAccount(), client);

        if(!accountExist){
            return new ResponseEntity<>("The destination account is not valid", HttpStatus.FORBIDDEN);
        }


        Account accountCredit = accountService.getAccountByNumber(loanAplicationDTO.numberAccount());

        if(accountCredit == null){
            return new ResponseEntity<>("The destination account does not exist " + loan.getPayments(), HttpStatus.FORBIDDEN);
        }



        ClientLoan clientLoan = new ClientLoan((loanAplicationDTO.amount() * 1.2), loanAplicationDTO.payments());
        Transaction transaction = new Transaction(loanAplicationDTO.amount(),loan.getName(), LocalDateTime.now(), TransactionType.CREDIT);
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);
        accountCredit.addTransaction(transaction);
        accountCredit.setBalance(accountCredit.getBalance() + loanAplicationDTO.amount());
        clientService.saveClient(client);
        accountService.saveAccount(accountCredit);
        transactionService.saveTransaction(transaction);
        loanService.saveLoan(loan);
        clientLoanService.saveClientLoan(clientLoan);


        return new ResponseEntity<>("Loan created. Total to pay: " + clientLoan.getAmount() + ". " + "Payments: " + clientLoan.getPayments()
                + ". Unit value of each payment: " + clientLoan.getAmount() / clientLoan.getPayments(), HttpStatus.CREATED);
    }

}

