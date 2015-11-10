package org.itallix.restaurant.services.impl;

import org.itallix.restaurant.Constants;
import org.itallix.restaurant.dao.repository.AccountRepository;
import org.itallix.restaurant.dao.repository.MenuRepository;
import org.itallix.restaurant.dao.repository.RestaurantRepository;
import org.itallix.restaurant.models.persistence.Account;
import org.itallix.restaurant.models.persistence.Menu;
import org.itallix.restaurant.models.persistence.Restaurant;
import org.itallix.restaurant.security.CurrentUser;
import org.itallix.restaurant.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired(required = false)
    private CurrentUser currentUser;

    @Override
    @Transactional
    public void createRestaurant(String name) {
        String userName = currentUser
                .getName()
                .orElseThrow(() -> new IllegalStateException("can't find current user in context"));
        Optional<Account> account = accountRepository.findByLogin(userName);
        account.ifPresent(a -> {
            String role = a.getRole();
            if (role.equals(Constants.ADMIN)) {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(name);
                restaurantRepository.save(restaurant);
            } else {
                throw new IllegalStateException("can't create restaurant for role USER");
            }
        });
        account.orElseThrow(() -> new IllegalStateException("Can't find account with name [" + userName + "]"));
    }

    @Override
    @Transactional
    public void createMenu(String restaurantName, Map<String, Double> dishes) {
        String userName = currentUser
                .getName()
                .orElseThrow(() -> new IllegalStateException("can't find current user in context"));
        Optional<Account> account = accountRepository.findByLogin(userName);
        account.ifPresent(a -> {
            String role = a.getRole();
            if (role.equals(Constants.ADMIN)) {
                List<Menu> menu = new ArrayList<>();
                Restaurant restaurant = restaurantRepository.findByName(restaurantName).get();
                dishes.forEach((name, price) -> {
                    Menu item = new Menu();
                    item.setRestaurant(restaurant);
                    item.setDishName(name);
                    item.setPrice(price);
                    menu.add(item);
                });
                menuRepository.save(menu);
            } else {
                throw new IllegalStateException("can't create restaurant for role USER");
            }
        });
        account.orElseThrow(() -> new IllegalStateException("Can't find account with name [" + userName + "]"));
    }
}
