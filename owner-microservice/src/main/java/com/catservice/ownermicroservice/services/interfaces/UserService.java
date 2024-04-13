package com.catservice.ownermicroservice.services.interfaces;

import com.catservice.ownermicroservice.models.UserEntity;

import java.util.UUID;

public interface UserService {
    boolean isCurrentUserEntityEquals(String username);
    void addUserEntity(UserEntity userEntity);
    void updateUserEntity(UserEntity userEntity);
    void deleteUserEntity(UserEntity userEntity);
    UserEntity getUserEntityById(UUID userEntityId);
    UserEntity getUserEntityByUsername(String username);
}
