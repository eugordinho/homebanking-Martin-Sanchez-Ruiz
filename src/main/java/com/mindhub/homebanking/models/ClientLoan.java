package com.mindhub.homebanking.models;

import jakarta.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private double amount;
    private int payments;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public ClientLoan() {
    }

    public ClientLoan( double amount, int payments) {
        this.amount = amount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
