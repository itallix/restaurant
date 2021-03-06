package org.itallix.restaurant.dao.repository;

import org.itallix.restaurant.models.persistence.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VoteRepository extends CrudRepository<Vote, Integer> {

    Optional<Vote> findByAccountId(int accountId);
}
