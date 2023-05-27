package com.cardswapshop.service;



import com.cardswapshop.model.Collection;
import com.cardswapshop.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CollectionService {
    private final CollectionRepository collectionRepository;

    //Constructor parametrizado
    @Autowired
    public CollectionService(CollectionRepository collectionRepository){
        this.collectionRepository = collectionRepository;
    }

    //Método que devuelve la lista de todas las colecciones
    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    //Método que devuelve un optional de la posible colección encontrada por el id de esta en DB
    public Optional<Collection> findById(Long id) {
        return collectionRepository.findById(id);
    }

    //Método para guardar la colección recibida por parámetro en DB
    public void save(Collection collection){
        collectionRepository.save(collection);
    }

    //Método para borrar la colección de la DB por el id de esta recibido por parámetro
    public void delete(Long id) {
        collectionRepository.deleteById(id);
    }

    //Método para actualizar la colección recibida por parámetro en DB
    public void update(Collection collection) {
        collectionRepository.save(collection);
    }
}
