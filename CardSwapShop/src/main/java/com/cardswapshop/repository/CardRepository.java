package com.cardswapshop.repository;

import com.cardswapshop.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCollectionId(Long id);
    List<Card> findByNameAllIgnoreCase(String name);
    List<Card> findByUserId(Long id);
}
