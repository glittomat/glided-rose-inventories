package com.miw.gildedrose.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miw.gildedrose.domain.Item;

/**
 * 
 * GlidedRoseRepository- Repository layer for Item Operations.
 *
 */
@Repository
public interface GildedRoseRepository extends JpaRepository<Item, Integer> {

	/**
	 * The repository call to find by Item name.
	 * 
	 * @param name
	 * @return
	 */
	public Item findByName(String name);
}
