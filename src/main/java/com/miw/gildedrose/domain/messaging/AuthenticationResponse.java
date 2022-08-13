package com.miw.gildedrose.domain.messaging;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class AuthenticationResponse.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8023940059998004307L;

	/**
	 * The String Jwt.
	 */
	private String jwt;
}
