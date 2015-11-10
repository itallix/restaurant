package com.itallix.restaurant.dao.repository;

import com.itallix.restaurant.models.persistence.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

    Optional<Restaurant> findByName(String name);
}
