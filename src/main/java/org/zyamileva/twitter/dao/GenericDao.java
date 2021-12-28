package org.zyamileva.twitter.dao;

import org.zyamileva.twitter.entities.PersistentEntity;

import java.util.Optional;
import java.util.UUID;

public interface GenericDao<T extends PersistentEntity> {
    T save(T entity);

    Optional<T> findById(UUID id);

    Iterable<T> findAll();

    void delete(T entity);
}