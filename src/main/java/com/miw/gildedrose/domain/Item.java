package com.miw.gildedrose.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * The class Item.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item implements Serializable // SearchEntity,
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2867929509389260601L;

	/**
	 * The Id of an item. Primary Key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/**
	 * Item name.
	 */
	@Schema(description = "The InventoryName", required = true)
	@JsonPropertyDescription(value = "JSON The InventoryName.")
	@Column(nullable = false)
	public String name;

	/**
	 * Item Description.
	 */
	@Schema(description = "The Inventory Description.", required = true)
	@JsonPropertyDescription(value = "JSON The Inventory Description.")
	@Column(nullable = false)
	public String description;

	/**
	 * The price of an item.
	 */
	@Schema(description = "The Inventory Price", required = true)
	@JsonPropertyDescription(value = "JSON The Inventory Price")
	@Column(nullable = false)
	public double price;

	/**
	 * An item can fall under multiple orders. This association with BuyItemDetails helps
	 * to keep track of the OrderId & the related items in an order.
	 */
	@JsonIgnore
	@ManyToMany(mappedBy = "itemEntities")
	@ToString.Exclude
	private List<BuyItemDetails> orderItems;

	/**
	 * Overridden Hashcode for Item
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).append(this.name).toHashCode();
	}

	/**
	 * Overridden Equals method for Item
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item)) {
			return false;
		}
		if (obj == this)
			return true;

		Item item = (Item) obj;
		return new EqualsBuilder().append(this.id, item.id).append(this.name, item.name).isEquals();
	}
}
