package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.entities.User;

import java.util.Optional;
import java.util.UUID;

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
    public Iterable<User> findAll() {
        return Storage.getInstance().findAllUsers();
    }

    @Override
    public void delete(User entity) {
        Storage.getInstance().deleteUser(entity);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Iterable<User> allUsers = Storage.getInstance().findAllUsers();
        for (User user : allUsers) {
            if (user.getLogin().equals(login)) {
                return Optional.of(user.clone());
            }
        }
        return Optional.empty();
    }
}