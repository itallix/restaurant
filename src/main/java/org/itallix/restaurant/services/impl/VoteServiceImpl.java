package org.itallix.restaurant.services.impl;

import org.itallix.restaurant.Constants;
import org.itallix.restaurant.dao.repository.AccountRepository;
import org.itallix.restaurant.dao.repository.RestaurantRepository;
import org.itallix.restaurant.dao.repository.VoteRepository;
import org.itallix.restaurant.models.persistence.Account;
import org.itallix.restaurant.models.persistence.Restaurant;
import org.itallix.restaurant.models.persistence.Vote;
import org.itallix.restaurant.models.security.User;
import org.itallix.restaurant.security.CurrentUser;
import org.itallix.restaurant.services.VoteService;
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
        User user = currentUser
                .getUser()
                .orElseThrow(() -> new IllegalStateException("can't find current user in context"));
        if (user.getRole().equals(Constants.USER)) {
            Restaurant restaurant = restaurantRepository.findByName(restaurantName).get();
            Account account = accountRepository.findByLogin(user.getName()).get();
            Optional<Vote> vote = voteRepository.findByAccountId(account.getId());
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
                v.setAccount(account);
                v.setRestaurant(restaurant);
                voteRepository.save(v);
            }
        } else {
            throw new IllegalStateException("can't create restaurant for role USER");
        }
    }
}
