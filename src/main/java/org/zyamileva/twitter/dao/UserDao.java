package org.zyamileva.twitter.dao;

import org.zyamileva.twitter.entities.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByLogin(String login);
    Set<User> findByIds(Set<UUID> ids);
    boolean existsById (UUID id);
}