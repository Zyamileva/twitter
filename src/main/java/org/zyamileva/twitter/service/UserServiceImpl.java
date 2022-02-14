package org.zyamileva.twitter.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.entities.User;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = Context.getInstance().getDaoFactory().createUserDao();
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
    private static final int MIN_LENGTH_LOGIN = 3;
    private static final int MAX_LENGTH_LOGIN = 14;
    private static final int MIN_LENGTH_USER_NAME = 2;
    private static final int MAX_LENGTH_USER_NAME = 20;

    @Override
    public Optional<User> saveUser(User user) {
        Set<String> validationErrors = validateUser(user);
        if (validationErrors.isEmpty()) {
            return Optional.of(userDao.save(user));
        }
        log.error(validationErrors);
        return Optional.empty();
    }

    static boolean validateLogin(String login) {
        boolean match;
        match = Pattern.compile("@_+").matcher(login).matches();
        if (match) {
            return !match;
        }
        String regex = "@[a-z_]{" + (MIN_LENGTH_LOGIN - 1) + "," + (MAX_LENGTH_LOGIN - 1) + "}";
        match = Pattern.compile(regex).matcher(login).matches();
        return match;
    }

    private boolean validateUserName(String login) {
        String regex = ".{" + (MIN_LENGTH_USER_NAME) + "," + (MAX_LENGTH_USER_NAME) + "}";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(login);
        boolean match = mat.matches();
        return match;
    }

    private Set<String> validateUser(User user) {
        Set<String> validationErrors = new HashSet<>();
        if (user == null) {
            validationErrors.add("User can't be null");
            return validationErrors;
        }
        if (user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            validationErrors.add("Login must not be empty or blank");
        }
        if (user.getUsername().isEmpty() || user.getUsername().isBlank()) {
            validationErrors.add("Username must not be empty or blank");
        }
        if (!validateLogin(user.getLogin())) {
            validationErrors.add("Login must start with the @ sign. It can contain an underscore character " +
                    "and at least one lowercase letter. It must be at least 3 and no more than 14 characters");
        }
        if (!validateUserName(user.getUsername())) {
            validationErrors.add("The username can contain letters, numbers, a space character and be at " +
                    "least 2 characters and no more than 20 characters");
        }
        if (validationErrors.isEmpty()) {
            validationErrors.addAll(validateIfLoginAvailable(user));
        }
        return validationErrors;
    }

    private Set<String> validateIfLoginAvailable(User user) {
        Set<String> validationErrors = new HashSet<>();
        if (user.getId() == null) {
            userDao.findByLogin(user.getLogin()).ifPresent(existedUser
                    -> validationErrors.add("This login: " + user.getLogin() + " is already taken"));
        } else {
            User existingUser = userDao.findById(user.getId()).orElseThrow();
            if (!existingUser.getLogin().equals(user.getLogin())) {
                userDao.findByLogin(user.getLogin()).ifPresent(existedUser ->
                        validationErrors.add("This login: " + user.getLogin() + " is already taken"));
            }
        }
        return validationErrors;
    }

    private boolean manageSubscriptions(UUID initialUserId, UUID subscriberUserId, BiConsumer<User, User> action) {
        if (initialUserId.equals(subscriberUserId)) {
            log.error("You can't subscribe youself");
            return false;
        }
        Optional<User> initialUserOptional = userDao.findById(initialUserId);
        if (initialUserOptional.isEmpty()) {
            log.error("Invalid userId passed: " + initialUserId);
            return false;
        }
        Optional<User> subscriberUserOptional = userDao.findById(subscriberUserId);
        if (subscriberUserOptional.isEmpty()) {
            log.error("Invalid userId passed: " + subscriberUserId);
            return false;
        }

        action.accept(initialUserOptional.get(), subscriberUserOptional.get());
        return true;
    }

    @Override
    public boolean subscribe(UUID initialUserId, UUID subscriberUserId) {
        BiConsumer<User, User> subscribeConsumer = (initialUser, subscriberUser) -> {
            initialUser.getFollowingIds().add(subscriberUser.getId());
            subscriberUser.getFollowerIds().add(initialUser.getId());
            userDao.save(initialUser);
            userDao.save(subscriberUser);

            log.info(initialUser.getLogin() + " subscribed to " + subscriberUser.getLogin());
        };
        return manageSubscriptions(initialUserId, subscriberUserId, subscribeConsumer);
    }

    @Override
    public boolean unsubscribe(UUID initialUserId, UUID subscriberUserId) {
        BiConsumer<User, User> subscribeConsumer = (initialUser, subscriberUser) -> {
            initialUser.getFollowingIds().remove(subscriberUser.getId());
            subscriberUser.getFollowerIds().remove(initialUser.getId());

            userDao.save(initialUser);
            userDao.save(subscriberUser);

            log.info(initialUser.getLogin() + " unsubscribed from " + subscriberUser.getLogin());
        };
        return manageSubscriptions(initialUserId, subscriberUserId, subscribeConsumer);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userDao.findById(id);
    }

    @Override
    public Set<User> findByIds(Set<UUID> ids) {
        return ids.isEmpty() ? Collections.emptySet() : userDao.findByIds(ids);
    }

    @Override
    public boolean existById(UUID id) {
        return userDao.existsById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }
}