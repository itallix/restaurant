package org.itallix.restaurant.dao.repository;

import org.itallix.restaurant.models.persistence.Menu;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
}
