package com.miw.gildedrose.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class UserDao.
 *
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDao implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6049576823852731470L;

	/**
	 * The ID/Primary Key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * The username.
	 */
	@Column(nullable = false)
	private String username;

	/**
	 * The password.
	 */
	@Column(nullable = false)
	@JsonIgnore
	private String password;
}
