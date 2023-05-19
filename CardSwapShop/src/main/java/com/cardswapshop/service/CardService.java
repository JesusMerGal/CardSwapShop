package com.cardswapshop.service;

import com.cardswapshop.controller.card.request.CardRequest;
import com.cardswapshop.controller.card.response.CardResponse;
import com.cardswapshop.controller.card.response.ListCardResponse;
import com.cardswapshop.model.Card;
import com.cardswapshop.model.Collection;
import com.cardswapshop.model.User;
import com.cardswapshop.repository.CardRepository;
import com.cardswapshop.repository.CollectionRepository;
import com.cardswapshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardService {

    private CardRepository cardRepository;
    private UserRepository userRepository;
    private CollectionRepository collectionRepository;

    public CardService() {
    }

    @Autowired
    public CardService(CardRepository cardRepository, UserRepository userRepository, CollectionRepository collectionRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.collectionRepository = collectionRepository;
    }

    public void save(CardRequest card, String username){
        User user = userRepository.getUser(username);
        Collection collection = collectionRepository.findById(card.getCollection_id()).get();
        Card newcard = convertRequestToCard(card,user,collection);
        cardRepository.save(newcard);
    }

    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    public void update(CardRequest card, Card currentCard) {
        currentCard.setName(card.getName());
        currentCard.setCard_number(card.getCard_number());
        currentCard.setFile_name(card.getFile_name());
        currentCard.setFile_type(card.getFile_type());
        currentCard.setCollection(collectionRepository.findById(card.getCollection_id()).get());
        currentCard.setImage(convertBase64toByteArray(card.getImage()));
        cardRepository.save(currentCard);
    }

    public ListCardResponse findAll() {
        return generateListCardsResponse(cardRepository.findAll());
    }

    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }
    public ListCardResponse findByCollectionId(Long id) {
        return generateListCardsResponse(cardRepository.findByCollectionId(id));
    }

    public ListCardResponse findByName(String title) {
        return generateListCardsResponse(cardRepository.findByNameAllIgnoreCase(title));
    }

    public ListCardResponse findByUserId(String currentUser) {
        User user = userRepository.getUser(currentUser);

        return generateListCardsResponse(cardRepository.findByUserId(user.getId()));
    }

    private Card convertRequestToCard(CardRequest card, User user, Collection collection){
        Card newcard = new Card();
        newcard.setName(card.getName());
        newcard.setCard_number(card.getCard_number());
        newcard.setFile_name(card.getFile_name());
        newcard.setFile_type(card.getFile_type());
        newcard.setUser(user);
        newcard.setCollection(collection);
        newcard.setImage(convertBase64toByteArray(card.getImage()));
        return newcard;
    }

    private CardResponse convertCardToResponse(Card card){
        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setName(card.getName());
        response.setCard_number(card.getCard_number());
        response.setFile_name(card.getFile_name());
        response.setFile_type(card.getFile_type());
        response.setUser(card.getUser());
        response.setCollection(card.getCollection());
        response.setImage(convertByteArrayToBase64(card.getImage()));
        return response;
    }

    private ListCardResponse generateListCardsResponse(List<Card> cards){
        ListCardResponse response = new ListCardResponse();
        List <CardResponse> listResponse = new ArrayList<>();
        cards.forEach(card ->{
            listResponse.add(convertCardToResponse(card));
        });
        response.setCards(listResponse);
        return response;
    }

    private String convertByteArrayToBase64(byte[] image){
        String codedImage = Base64.getEncoder().encodeToString(image);
        return codedImage;
    }

    private byte[] convertBase64toByteArray(String imageBase64){
        byte[] decodedString;
        decodedString = Base64.getDecoder().decode(imageBase64);
        return decodedString;
    }

}
