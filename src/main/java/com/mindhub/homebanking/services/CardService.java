package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface CardService {
    List<Card> getAllCards();

    List<CardDTO> getAllCardsDTO();

    List<Card> getAllCardsByClient(Client client);

    List<CardDTO> getAllCardsDTOByClient(Client client);

    /*Boolean getCardByTypeAndColorAndClient(CardType type, CardColor color, Client client);*/

    Card getCardByNumber(String number);

    void saveCard(Card card);


}
