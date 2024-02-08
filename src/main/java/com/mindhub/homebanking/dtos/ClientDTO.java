package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName, lastName, mail;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;

    public ClientDTO( Client client ) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.mail = client.getMail();
        this.accounts = client.getAccountSet().stream().map(AccountDTO::new).collect(Collectors.toSet());
        this.loans = client
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
}
