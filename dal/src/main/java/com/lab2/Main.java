package com.lab2;

import com.lab2.models.Cat;
import com.lab2.models.Color;
import com.lab2.models.Owner;
import com.lab2.repositories.impl.CatRepositoryImpl;
import com.lab2.repositories.impl.ColorRepositoryImpl;
import com.lab2.repositories.impl.OwnerRepositoryImpl;
import com.lab2.repositories.interfaces.CatRepository;
import com.lab2.repositories.interfaces.ColorRepository;
import com.lab2.repositories.interfaces.OwnerRepository;
import org.hibernate.query.QueryArgumentException;
import org.hibernate.type.descriptor.jdbc.UUIDJdbcType;

import java.util.Date;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ColorRepository colorRepository = new ColorRepositoryImpl();
        OwnerRepository ownerRepository = new OwnerRepositoryImpl();
        CatRepository catRepository = new CatRepositoryImpl();

        UUID id = UUID.fromString("01e6f675-13b2-4466-904d-8f9cdb7f7c24");

        Cat cat = catRepository.getById(id).orElseThrow(() -> new MissingFormatArgumentException("Id does not exists"));
        Owner owner = catRepository.getOwnerByCat(cat);
        List<Cat> friends = catRepository.getCatFriends(cat);

        for (Cat friend: friends) {
            System.out.println(friend.getName() + " " + friend.getId().toString());
        }
    }
}