package com.catservice.ownermicroservice.repositories;

import com.catservice.ownermicroservice.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    Optional<Owner> findOwnerByUsername(String username);
}
