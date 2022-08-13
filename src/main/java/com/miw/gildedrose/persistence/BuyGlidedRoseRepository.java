package com.miw.gildedrose.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miw.gildedrose.domain.BuyItemDetails;

/**
 * BuyGlidedRoseRepository- Repository layer for Buy Operations.
 *
 */
@Repository
public interface BuyGlidedRoseRepository extends JpaRepository<BuyItemDetails, Integer> {

}
