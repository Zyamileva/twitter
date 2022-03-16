package org.zyamileva.twitter.model;

import org.zyamileva.twitter.entities.User;

import java.util.Optional;
import java.util.Set;

public class CreateUserResponse {

    private final Optional<User> userOptional;
    private final Set<String> errors;

    public CreateUserResponse(Optional<User> userOptional, Set<String> errors) {
        this.userOptional = userOptional;
        this.errors = errors;
    }

    public Optional<User> getUserOptional() {
        return userOptional;
    }

    public Set<String> getErrors() {
        return errors;
    }
}