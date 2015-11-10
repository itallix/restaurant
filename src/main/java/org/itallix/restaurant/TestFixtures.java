package org.itallix.restaurant;

import org.itallix.restaurant.dao.repository.AccountRepository;
import org.itallix.restaurant.dao.repository.RestaurantRepository;
import org.itallix.restaurant.models.persistence.Account;
import org.itallix.restaurant.models.persistence.Restaurant;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * Created by greg.
 */
@Component
public class TestFixtures {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DataSource dataSource;

    public void initTestDb() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.migrate();
    }

    @Transactional
    public void createAccounts() {
        Account account = new Account();
        account.setLogin("admin");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPasswordHash(encoder.encode("password"));
        account.setRole(Constants.ADMIN);
        accountRepository.save(account);

        account = new Account();
        account.setLogin("user");
        account.setPasswordHash(encoder.encode("password"));
        account.setRole(Constants.USER);
        accountRepository.save(account);
    }

    @Transactional
    public void createRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Jamies");
        restaurantRepository.save(restaurant);
    }
}
