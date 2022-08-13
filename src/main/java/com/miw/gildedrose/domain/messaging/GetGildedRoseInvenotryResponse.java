package com.miw.gildedrose.domain.messaging;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.miw.gildedrose.domain.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class GetGildedRoseInvenotryResponse.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GetGildedRoseInvenotryResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8393269586229334916L;

	/**
	 * The Item returned as response. 
	 */
	private Item item;

}
