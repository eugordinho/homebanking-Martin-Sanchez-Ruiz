package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ClientLoanDTO {

    private Long id, loanId;
    private String name;
    private double amount;
    private int payments;

    public ClientLoanDTO( ClientLoan clientloan ){
        this.id = clientloan.getId();
        this.loanId = clientloan.getLoan().getId();
        this.name = clientloan.getLoan().getName();
        this.amount = clientloan.getAmount();
        this.payments = clientloan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
