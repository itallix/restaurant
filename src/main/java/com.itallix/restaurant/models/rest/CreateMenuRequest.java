package com.itallix.restaurant.models.rest;

import java.util.Map;

public class CreateMenuRequest {

    private String restaurantName;

    private Map<String, Double> menu;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Map<String, Double> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, Double> menu) {
        this.menu = menu;
    }
}
