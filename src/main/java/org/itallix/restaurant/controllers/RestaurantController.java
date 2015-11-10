package org.itallix.restaurant.controllers;

import org.itallix.restaurant.models.rest.CreateMenuRequest;
import org.itallix.restaurant.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createRestaurant(@RequestBody String name) {
        restaurantService.createRestaurant(name);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createMenu(@RequestBody CreateMenuRequest menuRequest) {
        restaurantService.createMenu(menuRequest.getRestaurantName(), menuRequest.getMenu());
    }
}
