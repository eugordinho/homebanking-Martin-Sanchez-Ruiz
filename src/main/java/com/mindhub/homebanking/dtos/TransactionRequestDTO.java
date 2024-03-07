package com.mindhub.homebanking.dtos;

public record TransactionRequestDTO (double amount, String description, String numberDebit, String numberCredit) {
}
