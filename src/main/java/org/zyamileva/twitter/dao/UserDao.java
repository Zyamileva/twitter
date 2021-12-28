package org.zyamileva.twitter.dao;

import org.zyamileva.twitter.entities.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByLogin(String login);
}