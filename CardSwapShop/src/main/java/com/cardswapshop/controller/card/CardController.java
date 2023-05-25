package com.cardswapshop.controller.card;

import com.cardswapshop.config.security.CurrentUser;
import com.cardswapshop.controller.auth.request.AuthenticationRequest;
import com.cardswapshop.controller.auth.response.AuthenticationResponse;
import com.cardswapshop.config.security.AuthenticationService;
import com.cardswapshop.controller.auth.request.RegisterRequest;
import com.cardswapshop.controller.card.request.CardRequest;
import com.cardswapshop.controller.card.response.CardResponse;
import com.cardswapshop.controller.card.response.ListCardResponse;
import com.cardswapshop.exceptions.BindingErrorsResponse;
import com.cardswapshop.model.Card;
import com.cardswapshop.model.User;
import com.cardswapshop.service.CardService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService service;

    @PostMapping("")
    public ResponseEntity<CardRequest> saveCard(
            @RequestBody CardRequest card, @CurrentUser UserDetails currentUser
    ) {
        HttpHeaders headers = new HttpHeaders();
        service.save(card,currentUser.getUsername());
        return new ResponseEntity<>(card, headers, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ListCardResponse> getMyCards(@CurrentUser UserDetails currentUser
    ) {
        ListCardResponse cards = service.findByUserId(currentUser.getUsername());
        if(cards == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if (cards.getCards().isEmpty()){
            return new ResponseEntity<>(cards, HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(cards, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ListCardResponse> getAllCards(@RequestParam(required = false) String name
    ) {
        if(name == null || name.isEmpty()) {
            ListCardResponse cards = service.findAll();
            if(cards == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else if (cards.getCards().isEmpty()){
                return new ResponseEntity<>(cards, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(cards, HttpStatus.OK);
            }
        }else {
            ListCardResponse cards = service.findByName(name);
            if(cards == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else if (cards.getCards().isEmpty()){
                return new ResponseEntity<>(cards, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(cards, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(card -> new ResponseEntity<>(card, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CardRequest> updateCard(
            @PathVariable("id") Long id, @RequestBody CardRequest card,
            BindingResult bindingResult
    ){
        Optional<Card> currentCard = service.findById(id);
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (card == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        if (!currentCard.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.update(card, currentCard.get());
        return new ResponseEntity<>(card, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Card> deleteCard(
            @PathVariable("id") Long id
    ){
        Optional<Card> cardToDelete = service.findById(id);
        if (!cardToDelete.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.delete(id);
        return new ResponseEntity<>(cardToDelete.get(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/collection/{id}")
    public ResponseEntity<ListCardResponse> getAllCardsByCollectionId(@PathVariable("id") Long id) {
        ListCardResponse cards = service.findByCollectionId(id);
        if (cards.getCards().isEmpty())
            return new ResponseEntity<>(cards, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}