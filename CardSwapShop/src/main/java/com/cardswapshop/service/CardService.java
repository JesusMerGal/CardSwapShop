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

    //Constructor parametrizado
    @Autowired
    public CardService(CardRepository cardRepository, UserRepository userRepository, CollectionRepository collectionRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.collectionRepository = collectionRepository;
    }
    //Método que permite guardar una carta en la DB
    public void save(CardRequest card, String username){
        User user = userRepository.getUser(username);
        Collection collection = collectionRepository.findById(card.getCollection_id()).get();
        Card newcard = convertRequestToCard(card,user,collection);
        cardRepository.save(newcard);
    }

    //Método que permite borrar una carta en DB por el id de esta
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    //Método que permite actualizar los datos de una carta en que se encuentra actualmente en DB
    public void update(CardRequest card, Card currentCard) {
        currentCard.setName(card.getName());
        currentCard.setCard_number(card.getCard_number());
        currentCard.setFile_name(card.getFile_name());
        currentCard.setFile_type(card.getFile_type());
        currentCard.setCollection(collectionRepository.findById(card.getCollection_id()).get());
        currentCard.setImage(convertBase64toByteArray(card.getImage()));
        cardRepository.save(currentCard);
    }

    //Método que devuelve una response con una lista de cartas con todas las cartas que se encuentran en DB
    public ListCardResponse findAll() {
        return generateListCardsResponse(cardRepository.findAll());
    }

    //Método que vuelve una optional de una carta que haya sido o no encontrada en la DB por su id
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    //Método que devuelve una response con una lista de cartas con todas las cartas que se encuentran asociadas
    // a al id de una colección pasado por parámetro asociada a dichas cartas en DB
    public ListCardResponse findByCollectionId(Long id) {
        return generateListCardsResponse(cardRepository.findByCollectionId(id));
    }

    //Método que devuelve una response con una lista de cartas con todas las cartas que tienen como nombre el string
    // pasado por parámetro en DB
    public ListCardResponse findByName(String title) {
        return generateListCardsResponse(cardRepository.findByNameAllIgnoreCase(title));
    }

    //Método que devuelve una response con una lista de cartas con todas las cartas que se encuentran asociadas a al id de un user asociado a dichas cartas en DB
    public ListCardResponse findByUserId(String currentUser) {
        User user = userRepository.getUser(currentUser);

        return generateListCardsResponse(cardRepository.findByUserId(user.getId()));
    }

    //Método que convierte la request de una carta recibida en el controller al modelo que usa la DB
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

    //Método que convierte el modelo Card recibido de la DB a una response que devuelve el controller
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

    //Método que genera una response con una lista de responses de cartas para poder ser retornada por el controller
    private ListCardResponse generateListCardsResponse(List<Card> cards){
        ListCardResponse response = new ListCardResponse();
        List <CardResponse> listResponse = new ArrayList<>();
        cards.forEach(card ->{
            listResponse.add(convertCardToResponse(card));
        });
        response.setCards(listResponse);
        return response;
    }

    //Método que convierte un ByteArray en un String, codificando el ByteArray a Base64 para retornar la imagen de la carta en el controller
    private String convertByteArrayToBase64(byte[] image){
        String codedImage = Base64.getEncoder().encodeToString(image);
        return codedImage;
    }

    //Método que decodifica la imagen en Base64 recibida en el controller a un ByteArray para poder ser almacenada en DB
    private byte[] convertBase64toByteArray(String imageBase64){
        byte[] decodedString;
        decodedString = Base64.getDecoder().decode(imageBase64);
        return decodedString;
    }

}
