package com.lab2.repositories.interfaces;

import com.lab2.models.Cat;
import com.lab2.models.Owner;
import com.lab2.repositories.Dao;

import java.util.List;
import java.util.UUID;

public interface CatRepository extends Dao<Cat, UUID> {
    Owner getOwnerByCat(Cat cat);
    List<Cat> getCatFriends(Cat cat);
    void addCatFriend(Cat cat, Cat friendCat);
}
