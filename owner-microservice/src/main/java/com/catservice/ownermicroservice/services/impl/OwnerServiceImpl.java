package com.catservice.ownermicroservice.services.impl;

import com.catservice.ownermicroservice.exceptions.OwnerDoesNotExistsException;
import com.catservice.ownermicroservice.exceptions.UserEntityDoesNotExists;
import com.catservice.ownermicroservice.models.Owner;
import com.catservice.ownermicroservice.models.UserEntity;
import com.catservice.ownermicroservice.models.dto.OwnerDto;
import com.catservice.ownermicroservice.models.dto.OwnerPageResponse;
import com.catservice.ownermicroservice.repositories.OwnerRepository;
import com.catservice.ownermicroservice.repositories.UserEntityRepository;
import com.catservice.ownermicroservice.services.interfaces.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserEntityRepository userRepository;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, UserEntityRepository userRepository) {
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    public void addOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Override
    public Owner getOwnerById(UUID ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerDoesNotExistsException("Owner with current id does not exists"));
    }

    @Override
    public Owner getOwnerByUsername(String username) {
        return ownerRepository.findOwnerByUsername(username).orElseThrow(() -> new OwnerDoesNotExistsException("Owner with current username does not exists"));
    }

    @Override
    public OwnerPageResponse getAllOwnerDtoWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Owner> pageOfOwners = ownerRepository.findAll(pageable);

        List<Owner> ownersContent = pageOfOwners.getContent();
        List<OwnerDto> ownersDtoContent = ownersContent.stream().map(this::mapOwnerToDto).toList();

        return OwnerPageResponse.builder()
                .content(ownersDtoContent)
                .pageNumber(pageOfOwners.getNumber())
                .pageSize(pageOfOwners.getSize())
                .totalElement(pageOfOwners.getTotalElements())
                .totalPages(pageOfOwners.getTotalPages())
                .isLast(pageOfOwners.isLast())
                .build();
    }

    @Override
    public Owner addOrUpdateOwnerWithDtoByUsername(OwnerDto ownerDto, String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserEntityDoesNotExists("UserEntity with current username does not exists"));

        Owner owner = new Owner();
        owner.setUsername(user.getUsername());
        owner.setBirthday(ownerDto.getBirthday());
        owner.setId(user.getId());
        owner.setUser(user);

        user.setOwnerInfo(owner);

        userRepository.save(user);
        ownerRepository.save(owner);

        return owner;
    }

    @Override
    public OwnerDto getOwnerDtoByUsername(String username) {
        Owner owner = getOwnerByUsername(username);
        return mapOwnerToDto(owner);
    }

    private OwnerDto mapOwnerToDto(Owner owner) {
        return OwnerDto.builder()
                .birthday(owner.getBirthday())
                .build();
    }
}
