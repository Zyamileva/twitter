package org.zyamileva.twitter.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.Inmemory.UserInMemoryDao;
import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.entities.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserInMemoryDao();
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
    private static int MIN_LENGTH_LOGIN = 3;
    private int MAX_LENGTH_LOGIN = 14;
    private int MIN_LENGTH_USER_NAME = 2;
    private int MAX_LENGTH_USER_NAME = 20;

    @Override
    public Optional<User> saveUser(User user) {
        Set<String> validationErrors = validateUser(user);
        if (validationErrors.isEmpty()) {
            return Optional.of(userDao.save(user));
        }
        log.error(validationErrors);
        return Optional.empty();

    }

    private boolean validateLogin(String login) {
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
        return validationErrors;
    }

    private boolean manageSubscriptions(UUID initialUserId, UUID subscriberUserId, boolean follow) {
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
        User initialUser = initialUserOptional.get();
        User subscriberUser = subscriberUserOptional.get();
        if (follow) {
            initialUser.getFollowingIds().add(subscriberUserId);
            subscriberUser.getFollowerIds().add(initialUserId);
        } else {
            initialUser.getFollowingIds().remove(subscriberUserId);
            subscriberUser.getFollowerIds().remove(initialUserId);
        }
        userDao.save(initialUser);
        userDao.save(subscriberUser);
        String operation = follow ? "subscribed to " : "unsubscribed from ";
        log.error(initialUser.getLogin() + " " + operation + subscriberUser.getLogin());
        return true;
    }

    @Override
    public boolean subscribe(UUID initialUserId, UUID subscriberUserId) {
        manageSubscriptions(initialUserId, subscriberUserId, true);
        return true;
    }

    @Override
    public boolean unsubscribe(UUID initialUserId, UUID subscriberUserId) {
        manageSubscriptions(initialUserId, subscriberUserId, false);
        return true;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        if (validateLogin(login)) {
            return userDao.findByLogin(login);
        }
        return Optional.empty();
    }
}