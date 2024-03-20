package com.lab2.controllers;

import com.lab2.entities.ResponseEntity;
import com.lab2.entities.enums.ResponseStatus;
import com.lab2.exceptions.ArgumentException;
import com.lab2.models.Cat;
import com.lab2.models.Owner;
import com.lab2.repositories.impl.CatRepositoryImpl;
import com.lab2.repositories.impl.OwnerRepositoryImpl;
import com.lab2.repositories.interfaces.CatRepository;
import com.lab2.repositories.interfaces.OwnerRepository;

import java.util.List;
import java.util.UUID;

public class OwnerController {
    private final OwnerRepository ownerRepository = new OwnerRepositoryImpl();
    private final CatRepository catRepository = new CatRepositoryImpl();

    public ResponseEntity<Owner> getOwnerById(UUID id) {
        ResponseEntity<Owner> responseEntity = new ResponseEntity<>();
        try {
            responseEntity.setResultClass(ownerRepository.getById((id)).orElseThrow(() -> new ArgumentException("Owner with this ID does not exists")));
            responseEntity.setStatus(ResponseStatus.OK);
        } catch (Exception ex) {
            responseEntity.setStatus(ResponseStatus.FAILED);
        }

        return responseEntity;
    }

    public boolean addOwner(Owner owner) {
        try {
            ownerRepository.insert(owner);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteOwner(UUID id) {
        try {
            Owner owner = ownerRepository.getById(id).orElseThrow(() -> new ArgumentException("Owner with this ID does not exists"));
            ownerRepository.delete(owner);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateOwner(UUID id) {
        try {
            Owner owner = ownerRepository.getById(id).orElseThrow(() -> new ArgumentException("Owner with this ID does not exists"));
            ownerRepository.update(owner);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean addCatToOwner(UUID ownerId, UUID catId) {
        try {
            Owner owner = ownerRepository.getById(ownerId).orElseThrow(() -> new ArgumentException("Owner with this ID does not exists"));
            Cat cat = catRepository.getById(catId).orElseThrow(() -> new ArgumentException("Cat with this ID does not exists"));
            ownerRepository.addCatToOwner(owner, cat);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public ResponseEntity<List<Cat>> getAllCatsByOwner(UUID ownerId) {
        ResponseEntity<List<Cat>> responseEntity = new ResponseEntity<>();
        try {
            Owner owner = ownerRepository.getById(ownerId).orElseThrow(() -> new ArgumentException("Owner with this ID does not exists"));
            responseEntity.setResultClass(ownerRepository.getAllCatsByOwner(owner));
            responseEntity.setStatus(ResponseStatus.OK);
        }
        catch (Exception ex) {
            responseEntity.setStatus(ResponseStatus.FAILED);
        }

        return responseEntity;
    }
}
