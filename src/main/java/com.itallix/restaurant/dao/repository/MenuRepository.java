package com.itallix.restaurant.dao.repository;

import com.itallix.restaurant.models.persistence.Menu;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
}
