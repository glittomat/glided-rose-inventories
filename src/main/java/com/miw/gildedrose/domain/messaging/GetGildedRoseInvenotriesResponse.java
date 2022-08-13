package com.miw.gildedrose.domain.messaging;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.miw.gildedrose.domain.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class GetGildedRoseInvenotriesResponse.
 *
 */
@JsonIgnoreProperties({ "messages", "action" })
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GetGildedRoseInvenotriesResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5806030075431033853L;

	/**
	 * List of Items in a response.
	 */
	private List<Item> itemEntities;

}
