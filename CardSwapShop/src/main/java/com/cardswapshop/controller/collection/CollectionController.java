package com.cardswapshop.controller.collection;


import com.cardswapshop.controller.collection.response.AllCollectionsResponse;
import com.cardswapshop.model.Card;
import com.cardswapshop.model.Collection;
import com.cardswapshop.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("Duplicates")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection")
public class CollectionController {
    private final CollectionService collectionService;

    //LLamada API para obtener todas las colecciones
    @GetMapping("")
    public ResponseEntity<AllCollectionsResponse> getAllCollections() {
        List<Collection> allCollections = collectionService.findAll();
        AllCollectionsResponse allCollectionsResponse = new AllCollectionsResponse();
        allCollectionsResponse.setCollections(allCollections);
        if (allCollections == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (allCollections.isEmpty())
            return new ResponseEntity<>(allCollectionsResponse, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(allCollectionsResponse, HttpStatus.OK);
    }

    //LLamada API para obtener colección con el id pasado por pathparam
    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable("id") Long id) {
        return collectionService
                .findById(id)
                .map(collection -> new ResponseEntity<>(collection, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //LLamada API para guardar una colección pasada en la request
    @PostMapping("")
    public ResponseEntity<Collection> saveCollection(
            @RequestBody Collection collection, BindingResult bindingResult,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        HttpHeaders headers = new HttpHeaders();
        if (collection == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }
        collectionService.save(collection);
        return new ResponseEntity<>(collection, headers, HttpStatus.CREATED);
    }

    //LLamada API para borrar la colección con id pasado por pathparam
    @DeleteMapping("/{id}")
    public ResponseEntity<Collection> deleteCollection(@PathVariable("id") Long id) {
        Optional<Collection> collectionToDelete = collectionService.findById(id);
        if (!collectionToDelete.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        collectionService.delete(id);
        return new ResponseEntity<>(collectionToDelete.get(), HttpStatus.NO_CONTENT);
    }

    //LLamada API para actualizar la colección con id pasado por pathparam con los datos pasados por request
    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable("id") Long id, @RequestBody Collection collection,
                                                       BindingResult bindingResult){
        Optional<Collection> currentCollection = collectionService.findById(id);
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (collection == null)) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        if (!currentCollection.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        collectionService.update(collection);
        return new ResponseEntity<>(collection, HttpStatus.NO_CONTENT);
    }

}
