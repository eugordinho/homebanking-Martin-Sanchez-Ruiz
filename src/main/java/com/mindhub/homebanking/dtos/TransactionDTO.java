package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private double amount;
    private String description;
    private LocalDateTime transactionDate;
    private TransactionType type;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.transactionDate = transaction.getTransactionDate();
        this.type = transaction.getType();
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getType() {
        return type;
    }
}
