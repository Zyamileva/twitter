package org.zyamileva.twitter.service;

import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.model.CreateUserResponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    CreateUserResponse saveUser(User user);

    boolean subscribe(UUID initialUserId, UUID subscriberUserId);

    boolean unsubscribe(UUID initialUserId, UUID subscriberUserId);

    Optional<User> findById(UUID id);

    Set<User> findByIds(Set<UUID> ids);

    List<User> findAll();

    boolean existById(UUID id);

    Optional<User> findByLogin(String login);

    void delete(User user);
}