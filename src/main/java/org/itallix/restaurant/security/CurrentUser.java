package org.itallix.restaurant.security;

import org.itallix.restaurant.models.security.User;

import java.util.Optional;

@FunctionalInterface
public interface CurrentUser {

    Optional<User> getUser();

}
