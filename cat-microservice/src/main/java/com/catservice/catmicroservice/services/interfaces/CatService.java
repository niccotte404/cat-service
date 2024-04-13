package com.catservice.catmicroservice.services.interfaces;

import com.catservice.catmicroservice.models.Cat;
import com.catservice.catmicroservice.models.dto.CatDto;
import com.catservice.catmicroservice.models.dto.CatPageResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatService {
    Cat getCatById(UUID catId);
    Cat getCatByName(String catName);
    List<Cat> getCatsByOwnerId(UUID ownerId);
    List<Cat> getAllCats();
    void addCat(Cat cat);
    void updateCat(Cat cat);
    void deleteCat(Cat cat);
    void addOrUpdateCatWithDtoById(CatDto catDto, UUID id);
    CatPageResponse getAllCatDtoWithPagination(int pageNumber, int pageSize, UUID ownerId);
}
