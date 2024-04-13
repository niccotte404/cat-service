package com.catservice.catmicroservice.repositories;

import com.catservice.catmicroservice.models.Cat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CatRepository extends JpaRepository<Cat, UUID> {
    List<Cat> findAllByOwnerId(UUID ownerId);
    Cat findCatByName(String catName);
    Page<Cat> findAllByOwnerId(Pageable pageable, UUID ownerId);
}
