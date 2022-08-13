package com.miw.gildedrose.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.miw.gildedrose.domain.UserDao;

/**
 * User Repository.
 *
 */
@Repository
public interface UserRepository extends CrudRepository<UserDao, Integer> {
	UserDao findByUsername(String username);
}