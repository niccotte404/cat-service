package com.lab2.repositories;

import java.util.Collection;
import java.util.Optional;

public interface Dao<TClass, TId> {
    Optional<TClass> getById(TId id);
    Collection<TClass> getAll();
    TClass update(TClass tClass);
    void delete(TClass tClass);
    void insert(TClass tClass);
}
