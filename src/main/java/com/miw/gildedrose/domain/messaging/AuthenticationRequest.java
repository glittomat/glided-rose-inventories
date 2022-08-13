package com.miw.gildedrose.domain.messaging;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class AuthenticationRequest.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3491013264275304308L;

	/**
	 * The Username.
	 */
	private String username;

	/**
	 * The Password
	 */
	private String password;
}
