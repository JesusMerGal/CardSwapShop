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

    @Autowired
    public CollectionService(CollectionRepository collectionRepository){
        this.collectionRepository = collectionRepository;
    }

    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> findById(Long id) {
        return collectionRepository.findById(id);
    }

    public void save(Collection collection){
        collectionRepository.save(collection);
    }

    public void delete(Long id) {
        collectionRepository.deleteById(id);
    }

    public void update(Collection collection) {
        collectionRepository.save(collection);
    }
}
