package com.itallix.restaurant.services.impl;

import com.itallix.restaurant.Constants;
import com.itallix.restaurant.dao.repository.AccountRepository;
import com.itallix.restaurant.dao.repository.RestaurantRepository;
import com.itallix.restaurant.dao.repository.VoteRepository;
import com.itallix.restaurant.models.persistence.Account;
import com.itallix.restaurant.models.persistence.Restaurant;
import com.itallix.restaurant.models.persistence.Vote;
import com.itallix.restaurant.security.CurrentUser;
import com.itallix.restaurant.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired(required = false)
    private CurrentUser currentUser;

    @Override
    public void vote(String restaurantName) {
        String userName = currentUser
                .getName()
                .orElseThrow(() -> new IllegalStateException("can't find current user in context"));
        Optional<Account> account = accountRepository.findByLogin(userName);
        account.ifPresent(a -> {
            String role = a.getRole();
            if (role.equals(Constants.USER)) {
                Restaurant restaurant = restaurantRepository.findByName(restaurantName).get();
                Optional<Vote> vote = voteRepository.findByAccountId(a.getId());
                vote.ifPresent(v -> {
                    if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
                        v.setRestaurant(restaurant);
                        voteRepository.save(v);
                    } else {
                        throw new IllegalStateException("too late: vote can't be changed");
                    }
                });
                if (!vote.isPresent()) {
                    Vote v = new Vote();
                    v.setAccount(a);
                    v.setRestaurant(restaurant);
                    voteRepository.save(v);
                }
            } else {
                throw new IllegalStateException("can't create restaurant for role USER");
            }
        });
        account.orElseThrow(() -> new IllegalStateException("Can't find account with name [" + userName + "]"));
    }
}
