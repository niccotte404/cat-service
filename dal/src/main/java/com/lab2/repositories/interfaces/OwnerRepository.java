package com.lab2.repositories.interfaces;

import com.lab2.models.Owner;
import com.lab2.repositories.Dao;

import java.util.UUID;

public interface OwnerRepository extends Dao<Owner, UUID> {
}
