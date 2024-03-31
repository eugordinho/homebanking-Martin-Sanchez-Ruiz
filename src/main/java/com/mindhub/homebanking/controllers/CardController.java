package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CardRequestDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/clients/current")
public class CardController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;

    @Autowired
    private RandomNumber randomNumber;

    @GetMapping("/cards")
    public ResponseEntity<?> getCards() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);

        return ResponseEntity.ok(cardService.getAllCardsDTOByClient(client));

    }

    @PostMapping("/cards")
    public ResponseEntity<?> createCard (@RequestBody CardRequestDTO cardRequestDTO){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);


        if(cardRequestDTO.type().isBlank()){
            return new ResponseEntity<>("The type field cannot be empty", HttpStatus.FORBIDDEN);

        }

        if(cardRequestDTO.color().isBlank()){
            return new ResponseEntity<>("The color field cannot be empty", HttpStatus.FORBIDDEN);
        }

/*        Boolean cardExist = cardService.getCardByTypeAndColorAndClient(CardType.valueOf(cardRequestDTO.type()), CardColor.valueOf(cardRequestDTO.color()), client);

        if(cardExist){
            return new ResponseEntity<>("You already have one card with type " + cardRequestDTO.type() + " and color " +
                    cardRequestDTO.color(), HttpStatus.FORBIDDEN);
        }*/


        String cardNumber;
        do {
            cardNumber = String.format("%04d", RandomNumber.getRandomNumber(0, 10000)) + " - "
                    + String.format("%04d", RandomNumber.getRandomNumber(0, 10000)) + " - "
                    + String.format("%04d", RandomNumber.getRandomNumber(0, 10000)) + " - "
                    + String.format("%04d", RandomNumber.getRandomNumber(0, 10000));
        } while (cardService.getCardByNumber(cardNumber) != null);

        String cvv = String.format("%03d", RandomNumber.getRandomNumber(0, 1000));



        Card card = new Card(cardNumber, cvv, CardColor.valueOf(cardRequestDTO.color()), CardType.valueOf(cardRequestDTO.type()), LocalDate.now());
        client.addCards(card);
        clientService.saveClient(client);
        cardService.saveCard(card);

        return new ResponseEntity<>("Card created", HttpStatus.CREATED);
    }



}
