package com.itallix.restaurant.dao.repository;

import com.itallix.restaurant.models.persistence.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    Optional<Account> findByLogin(String login);

}
