package org.zyamileva.twitter.dao.inmemory;

import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserInMemoryDao implements UserDao {
    @Override
    public User save(User entity) {
        return Storage.getInstance().putUser(entity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        User user = Storage.getInstance().getByUserId(id);
        return Optional.ofNullable(user);
    }

    @Override
    public List findAll() {
        return Storage.getInstance().findAllUsers();
    }

    @Override
    public User delete(User entity) {
        Storage.getInstance().deleteUser(entity);
        return entity;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Storage.getInstance().findAllUsers()
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Set<User> findByIds(Set<UUID> ids) {
        return Storage.getInstance().findAllUsers()
                .stream()
                .filter(user -> ids.contains(user.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean existsById(UUID id) {
        return Storage.getInstance().findAllUsers()
                .stream()
                .anyMatch(ids -> ids.getId().equals(id));
    }
}