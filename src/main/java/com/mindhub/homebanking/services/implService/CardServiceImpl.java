package com.mindhub.homebanking.services.implService;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Override
    public List<Card> getAllCards() {
        return null;
    }

    @Override
    public List<CardDTO> getAllCardsDTO() {
        return null;
    }

    @Override
    public List<Card> getAllCardsByClient(Client client) {
        return null;
    }

    @Override
    public List<CardDTO> getAllCardsDTOByClient(Client client) {
        return null;
    }

    @Override
    public Boolean getCardByTypeAndColorAndClient(CardType type, CardColor color, Client client) {
        return null;
    }

    @Override
    public Card getCardByNumber(String number) {
        return null;
    }

    @Override
    public void saveCard(Card card) {

    }
}
