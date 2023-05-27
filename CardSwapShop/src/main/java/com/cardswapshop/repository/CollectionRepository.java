package com.cardswapshop.repository;

import com.cardswapshop.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    //No hay ningún método creado en este repositorio ya que solo se están usando los métodos heredados de JpaRepository.
}
