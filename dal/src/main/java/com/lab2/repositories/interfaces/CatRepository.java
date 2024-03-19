package com.lab2.repositories.interfaces;

import com.lab2.models.Cat;
import com.lab2.repositories.Dao;

import java.util.UUID;

public interface CatRepository extends Dao<Cat, UUID> {
}
