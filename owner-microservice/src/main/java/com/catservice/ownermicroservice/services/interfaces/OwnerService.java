package com.catservice.ownermicroservice.services.interfaces;

import com.catservice.ownermicroservice.models.Owner;
import com.catservice.ownermicroservice.models.dto.OwnerDto;
import com.catservice.ownermicroservice.models.dto.OwnerPageResponse;

import java.util.UUID;

public interface OwnerService {
    void updateOwner(Owner owner);
    void addOwner(Owner owner);
    void deleteOwner(Owner owner);
    Owner getOwnerById(UUID ownerId);
    Owner getOwnerByUsername(String username);
    OwnerPageResponse getAllOwnerDtoWithPagination(int pageNumber, int pageSize);
    Owner addOrUpdateOwnerWithDtoByUsername(OwnerDto ownerDto, String username);
    OwnerDto getOwnerDtoByUsername(String username);
}
