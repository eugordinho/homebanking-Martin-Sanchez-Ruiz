package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping( "/api/loans" )
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans(){
        List<Loan> loan = loanRepository.findAll();

        return new ResponseEntity<>(loan.stream().map(LoanDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }
}
