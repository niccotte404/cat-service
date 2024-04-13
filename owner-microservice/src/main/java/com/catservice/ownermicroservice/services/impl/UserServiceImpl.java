package com.catservice.ownermicroservice.services.impl;

import com.catservice.ownermicroservice.models.UserEntity;
import com.catservice.ownermicroservice.repositories.UserEntityRepository;
import com.catservice.ownermicroservice.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userRepository;
    @Autowired
    public UserServiceImpl(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isCurrentUserEntityEquals(String username) {
        String actualUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return actualUser.equals(username);
    }

    @Override
    public void addUserEntity(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void updateUserEntity(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUserEntity(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public UserEntity getUserEntityById(UUID userEntityId) {
        return userRepository.findById(userEntityId).orElseThrow(() -> new UsernameNotFoundException("UseEntity with current id does not exists"));
    }

    @Override
    public UserEntity getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("UseEntity with current username does not exists"));
    }
}
