package com.catservice.catmicroservice.services.impl;

import com.catservice.catmicroservice.exceptions.CatNotFoundException;
import com.catservice.catmicroservice.models.Cat;
import com.catservice.catmicroservice.models.dto.CatDto;
import com.catservice.catmicroservice.models.dto.CatPageResponse;
import com.catservice.catmicroservice.repositories.CatRepository;
import com.catservice.catmicroservice.services.interfaces.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;

    @Autowired
    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public Cat getCatById(UUID catId) {
        return catRepository.findById(catId).orElseThrow(() -> new CatNotFoundException("Cat with current id not found"));
    }

    @Override
    public Cat getCatByName(String catName) {
        return catRepository.findCatByName(catName);
    }

    @Override
    public List<Cat> getCatsByOwnerId(UUID ownerId) {
        return catRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }

    @Override
    public void addCat(Cat cat) {
        catRepository.save(cat);
    }

    @Override
    public void updateCat(Cat cat) {
        catRepository.save(cat);
    }

    @Override
    public void deleteCat(Cat cat) {
        catRepository.delete(cat);
    }

    @Override
    public void addOrUpdateCatWithDtoById(CatDto catDto, UUID id) {
        Cat cat = catRepository.findById(id).orElseThrow(() -> new CatNotFoundException("Cat with current username does not exists"));

        Cat catResult = new Cat();
        catResult.setName(catDto.getName());
        catResult.setBreed(catDto.getBreed());
        catResult.setBirthday(catDto.getBirthday());
        catResult.setColor(catDto.getColor());

        catResult.setOwnerId(cat.getOwnerId());
        catResult.setId(cat.getId());

        catRepository.save(catResult);
    }

    @Override
    public CatPageResponse getAllCatDtoWithPagination(int pageNumber, int pageSize, UUID ownerId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Cat> pageOfOwners = catRepository.findAllByOwnerId(pageable, ownerId);

        List<Cat> ownersContent = pageOfOwners.getContent();
        List<CatDto> ownersDtoContent = ownersContent.stream().map(this::mapCatToDto).toList();

        return CatPageResponse.builder()
                .content(ownersDtoContent)
                .pageNumber(pageOfOwners.getNumber())
                .pageSize(pageOfOwners.getSize())
                .totalElement(pageOfOwners.getTotalElements())
                .totalPages(pageOfOwners.getTotalPages())
                .isLast(pageOfOwners.isLast())
                .build();
    }

    private CatDto mapCatToDto(Cat cat) {
        return CatDto.builder()
                .color(cat.getColor())
                .breed(cat.getBreed())
                .name(cat.getName())
                .birthday(cat.getBirthday())
                .build();
    }
}
