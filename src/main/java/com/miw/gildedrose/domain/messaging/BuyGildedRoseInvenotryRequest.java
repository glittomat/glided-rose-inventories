package com.miw.gildedrose.domain.messaging;

import java.io.Serializable;
import com.miw.gildedrose.domain.BuyItemDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class BuyGildedRoseInvenotryRequest.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyGildedRoseInvenotryRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 537371041683462687L;

	/**
	 * BuyItems requested.
	 */
	private BuyItemDetails buyItems;
}
