package com.lab2.controllers;

import com.lab2.entities.ResponseEntity;
import com.lab2.entities.enums.ResponseStatus;
import com.lab2.exceptions.ArgumentException;
import com.lab2.models.Cat;
import com.lab2.models.Owner;
import com.lab2.repositories.impl.CatRepositoryImpl;
import com.lab2.repositories.interfaces.CatRepository;

import java.util.List;
import java.util.UUID;

public class CatController {
    private final CatRepository catRepository = new CatRepositoryImpl();

    public ResponseEntity<Cat> getCatById(UUID id) {
        ResponseEntity<Cat> responseEntity = new ResponseEntity<>();
        try {
            responseEntity.setResultClass(catRepository.getById((id)).orElseThrow(() -> new ArgumentException("Cat with this ID does not exists")));
            responseEntity.setStatus(ResponseStatus.OK);
        }
        catch (Exception ex) {
            responseEntity.setStatus(ResponseStatus.FAILED);
        }

        return responseEntity;
    }

    public boolean addCat(Cat cat) {
        try {
            catRepository.insert(cat);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteCat(UUID id) {
        try {
            Cat cat = catRepository.getById(id).orElseThrow(() -> new ArgumentException("Cat with this ID does not exists"));
            catRepository.delete(cat);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean updateCat(UUID id) {
        try {
            Cat cat = catRepository.getById(id).orElseThrow(() -> new ArgumentException("Cat with this ID does not exists"));
            catRepository.update(cat);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean addFriend(UUID catId, UUID friendId) {
        try {
            Cat cat = catRepository.getById(catId).orElseThrow(() -> new ArgumentException("Cat with this ID does not exists"));
            Cat friend = catRepository.getById(catId).orElseThrow(() -> new ArgumentException("Cat friend with this ID does not exists"));
            catRepository.addCatFriend(cat, friend);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public ResponseEntity<Owner> getCatOwner(UUID catId) {
        ResponseEntity<Owner> responseEntity = new ResponseEntity<>();
        try {
            Cat cat = catRepository.getById(catId).orElseThrow(() -> new ArgumentException("Cat with this ID does not exists"));
            responseEntity.setResultClass(catRepository.getOwnerByCat(cat));
            responseEntity.setStatus(ResponseStatus.OK);
        }
        catch (Exception ex) {
            responseEntity.setStatus(ResponseStatus.FAILED);
        }

        return responseEntity;
    }

    public ResponseEntity<List<Cat>> getCatFriends(UUID catId) {
        ResponseEntity<List<Cat>> responseEntity = new ResponseEntity<>();
        try {
            Cat cat = catRepository.getById(catId).orElseThrow(() -> new ArgumentException("Cat with this ID does not exists"));
            responseEntity.setResultClass(catRepository.getCatFriends(cat));
            responseEntity.setStatus(ResponseStatus.OK);
        }
        catch (Exception ex) {
            responseEntity.setStatus(ResponseStatus.FAILED);
        }

        return responseEntity;
    }
}
