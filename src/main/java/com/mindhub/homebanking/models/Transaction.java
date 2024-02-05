package com.mindhub.homebanking.models;

import com.mindhub.homebanking.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private double amount;
    private String description;
    private LocalDateTime transactionDate = LocalDateTime.now();
    private TransactionType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name = "Account_id")
    private Account accountOrigin;

    public Transaction() {
    }

    public Transaction(double amount, String description, TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transactionDate=" + transactionDate +
                ", type=" + type +
                '}';
    }

    public Account getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(Account accountOrigin) {
        this.accountOrigin = accountOrigin;
    }
}
