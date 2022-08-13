package com.miw.gildedrose.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.miw.gildedrose.domain.Item;

/**
 * The class SurgePriceUtil.
 *
 */
@Component
public class SurgePriceUtil {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SurgePriceUtil.class);
	
	/** The Surge price view limit. */
	private static final int COUNT_LIMIT = 10;
	
	/** Time limit of 1 hour in milliseconds. */
	private static final long TIME_LIMIT = 3600000L;
	
	/** Map containing required info of each item for surge price mechanism. */
	private static final Map<Item, HitCounter> itemHitMap = new HashMap<Item, HitCounter>();

	/**
	 * Inner class to hold the required information and for calculating surge price.
	 *
	 */
	public class HitCounter {
		/** Queue to hold timeStamps. */
		Queue<Long> queue = null;
		
		/** Intial price of an item. */
		Double initalPrice = null;

		public HitCounter(double initalPrice) {
			queue = new LinkedList<Long>();
			this.initalPrice = initalPrice;
		}

		/**
		 * Hit function to calculate the surge price based on required conditions.
		 * 
		 * @param timestamp
		 * @param currPrice
		 * @return
		 */
		public Double hit(long timestamp, Double currPrice) {
			// While loop for checking the item based on Time Limit, & if not viewed 10
			// times in last one hour, this will setback the price to the initial price.
			while (!queue.isEmpty() && timestamp - queue.peek() >= TIME_LIMIT) {
				queue.poll();
				if (queue.size() == 0) {
					queue.offer(timestamp);
					currPrice = this.initalPrice;
					return currPrice;
				}
			}

			// Condition when the view of an item is less than 10 times, in which case this will keep
			// the current price.
			if (queue.size() < COUNT_LIMIT) {
				queue.offer(timestamp);
				return currPrice;
			}
			
			// If the view of an item is more than 10 times within the time limit, then
			// surge the price by 10%
			queue.clear();
			queue.offer(timestamp);			
			currPrice = currPrice + currPrice * 0.1;
			currPrice = BigDecimal.valueOf(currPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
			logger.debug("Current price after modification: ", currPrice);
			return currPrice; 
		}
	}
	
	

	/**
	 * Function to calculate the Surge of an item. This is getting called from the
	 * Service layer.
	 * 
	 * @param item Item
	 * @return the calculated price
	 */
	public Double calculateSurgeAction(Item item) {
		HitCounter hitCounter = itemHitMap.get(item);
		long curTime = System.currentTimeMillis();

		// Insert into the map, if item not present in the map.
		if (hitCounter == null) {
			hitCounter = new HitCounter(item.getPrice());
			itemHitMap.put(item, hitCounter);
		}

		// Return the calculated price
		return hitCounter.hit(curTime, item.price);
	}
}