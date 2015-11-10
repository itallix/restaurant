package com.itallix.restaurant.security;

import java.util.Optional;

@FunctionalInterface
public interface CurrentUser {

    Optional<String> getName();
}
