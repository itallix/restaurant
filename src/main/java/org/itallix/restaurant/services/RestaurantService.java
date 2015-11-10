package org.itallix.restaurant.services;

import java.util.Map;

public interface RestaurantService {

    void createRestaurant(String name);

    void createMenu(String restaurantName, Map<String, Double> dishes);
}
