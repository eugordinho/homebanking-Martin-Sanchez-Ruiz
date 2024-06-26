package com.mindhub.homebanking.models;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;
    private String number, cvv;
    private LocalDate fromDate, thruDate;
    private CardColor colorType;
    private CardType cardType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name = "Client_id")
    private Client client;
    private String cardHolder;

    public Card(){}

    public Card(String number, String cvv, CardColor colorType, CardType cardType, LocalDate fromDate) {
        this.number = number;
        this.cvv = cvv;
        this.colorType = colorType;
        this.cardType = cardType;
        this.fromDate = fromDate;
        this.thruDate = LocalDate.now().plusYears(5);
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public CardColor getColorType() {
        return colorType;
    }

    public CardType getCardType() {
        return cardType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setColorType(CardColor colorType) {
        this.colorType = colorType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
}
