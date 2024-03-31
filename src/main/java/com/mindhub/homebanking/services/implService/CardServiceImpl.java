package com.mindhub.homebanking.services.implService;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<CardDTO> getAllCardsDTO() {
        return getAllCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<Card> getAllCardsByClient(Client client) {
        return cardRepository.findAllByClient(client);
    }

    @Override
    public List<CardDTO> getAllCardsDTOByClient(Client client) {
        return getAllCardsByClient(client).stream().map(CardDTO::new).collect(Collectors.toList());
    }

/*    @Override
    public Boolean getCardByTypeAndColorAndClient(CardType type, CardColor color, Client client) {
        return cardRepository.existsByTypeAndColorAndClient(type, color, client);
    }*/

    @Override
    public Card getCardByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
