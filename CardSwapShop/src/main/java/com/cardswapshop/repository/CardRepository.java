package com.cardswapshop.repository;

import com.cardswapshop.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    //Devuelve una lista de cartas por el id de la colección asociada a la carta en la DB
    List<Card> findByCollectionId(Long id);
    //Devuelve una lista de cartas por el nombre de la carta ignorando mayusculas y minusculas en la DB
    List<Card> findByNameAllIgnoreCase(String name);
    //Devuelve una lista de cartas por el id del user asociado a la carta en la DB
    List<Card> findByUserId(Long id);
}
