package org.zyamileva.twitter.service;

import org.zyamileva.twitter.entities.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    Optional<User> saveUser(User user);

    boolean subscribe(UUID initialUserId, UUID subscriberUserId);

    boolean unsubscribe(UUID initialUserId, UUID subscriberUserId);

    Optional<User> findById(UUID id);

    Set<User> findByIds(Set<UUID> ids);

    boolean existById(UUID id);

    Optional<User> findByLogin(String login);

    void delete(User user);
}