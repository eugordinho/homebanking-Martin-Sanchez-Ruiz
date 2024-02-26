package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {
    private Long id;
    private String cardHolder, number, cvv;
    private LocalDateTime fromDate, thruDate;
    private CardColor color;
    private CardType type;

    public CardDTO( Card card ) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder().getFirstName() +" " + card.getCardHolder().getLastName();
        this.type = card.getCardType();
        this.color = card.getColorType();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public CardColor getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }
}
