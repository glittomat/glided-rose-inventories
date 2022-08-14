package com.miw.gildedrose.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import com.miw.gildedrose.domain.UserDao;
import com.miw.gildedrose.persistence.UserRepository;

/**
 * The Class JwtUserDetailsServiceMockTest.
 *
 */
@SpringBootTest
@ActiveProfiles("test")
public class JwtUserDetailsServiceMockTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private JwtUserDetailsService userDetailsService;

	@Test
	public void usernameUserdetailsSucessTest() {
		final String username = "miw";
		final UserDao user = createUserDao();
		when(userRepository.findByUsername(username)).thenReturn(user);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		assertNotNull(userDetails);
		assertEquals(user.getUsername(), userDetails.getPassword());
	}

	@Test
	public void invalidUsernameUserdetailsTest() throws Exception {

		final String username = "test";
		final UserDao user = createUserDao();
		when(userRepository.findByUsername(username)).thenReturn(user);

		assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername(username);
		});
	}

	/**
	 * Function to create UserDao.
	 */
	private UserDao createUserDao() {
		UserDao user = new UserDao();
		user.setUsername("miw");
		user.setPassword("miw");
		return user;
	}
}
