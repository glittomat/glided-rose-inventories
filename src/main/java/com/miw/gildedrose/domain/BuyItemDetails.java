package com.miw.gildedrose.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class BuyItemDetails.
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyItemDetails implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2411113658406798981L;

	/**
	 * The OrderID
	 */
	@Id
	@GeneratedValue
	private Long orderId;

	/**
	 * The Items present in a Buy order. An order can contain multiple items.
	 */
	@Schema(description = "The InventoryItem", required = true)
	@JsonPropertyDescription(value = "JSON The InventoryItem.")
	@ManyToMany
	@JoinTable(name = "order_items", joinColumns = @JoinColumn(name = "buyItemDetails_orderId"), inverseJoinColumns = @JoinColumn(name = "item_id"))
	@ToString.Exclude
	private List<Item> itemEntities;

	/**
	 * The user info who placed the buy order.
	 */
	@Schema(description = "The InventoryItem", required = false)
	@JsonPropertyDescription(value = "JSON The InventoryItem.")
	private String userInfo;

	/**
	 * The time of Buy order.
	 */
	@Schema(description = "The InventoryItem", required = false)
	@JsonPropertyDescription(value = "JSON The InventoryItem.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime buyTime;

}
