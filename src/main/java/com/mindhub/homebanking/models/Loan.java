package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double maxAmount;
    @ElementCollection
    private Set<Integer> payments = new HashSet<>();
    @OneToMany( mappedBy = "loan")
    private Set<ClientLoan> clientloans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, double maxAmount, Set<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClientloans() {
        return clientloans;
    }

    public void setClientloans(Set<ClientLoan> clientloans) {
        this.clientloans = clientloans;
    }

    public void addClientLoan(ClientLoan clientloan ) {
        clientloan.setLoan( this );
        clientloans.add( clientloan );
    }

    public Set<Client> getClients( ClientLoan clientloan ){
        return clientloans.stream().map( client -> client.getClient()).collect( Collectors.toSet());
    }
}
