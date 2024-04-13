package com.catservice.catmicroservice.repositories;

import com.catservice.catmicroservice.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
