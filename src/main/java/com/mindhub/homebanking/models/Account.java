package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String number;
    private double balance;
    private LocalDate creationDate = LocalDate.now();
    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "Client_Id")
    private Client clientOwner;

    @OneToMany( mappedBy = "accountOrigin", fetch = FetchType.EAGER)
    private Set<Transaction> transactionSet = new HashSet<>();

    public Account(String number, double balance, LocalDate creationDate) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    public Account( ) { }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Client getClient() {
        return clientOwner;
    }

    public void setClient(Client client) {
        this.clientOwner = client;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                ", clientOwner=" + clientOwner +
                '}';
    }

    public Client getClientOwner() {
        return clientOwner;
    }

    public Set<Transaction> getTransactionSet() {
        return transactionSet;
    }

    public void addTransaction( Transaction transaction ){
        transaction.setAccountOrigin( this );
        transactionSet.add( transaction );
    }
}
