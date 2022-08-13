package com.miw.gildedrose.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.miw.gildedrose.service.JwtUserDetailsService;
import com.miw.gildedrose.util.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class JwTRequestFilterMockTest {

	@Mock
	private JwtUserDetailsService userDetailsService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtil jwtUtilTokenGenerator;

	@InjectMocks
	private JwtRequestFilter filterToTest;

	private MockHttpServletRequest mockRequest;
	private MockHttpServletResponse mockResponse;
	private MockFilterChain mockFilterChain;
	private UserDetails userDetails;
	
	@BeforeEach
	void setUp() {
		mockRequest = new MockHttpServletRequest();
		mockResponse = new MockHttpServletResponse();
		mockFilterChain = new MockFilterChain();
		userDetails = new User("miw", "miw", new ArrayList<>());
		String tokenValue = "Bearer " + jwtUtilTokenGenerator.generateToken(null);
		mockRequest.addHeader("Authorization", tokenValue);
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(null);
		SecurityContextHolder.clearContext();
	}

	
	@Test
	void testFilterExecution() throws ServletException, IOException {
		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
		MockFilterChain mockFilterChainSpy = spy(mockFilterChain);
		
		filterToTest.doFilter(mockRequest, mockResponse, mockFilterChainSpy);
		verify(mockFilterChainSpy, times(1)).doFilter(mockRequest, mockResponse);
	}

	
	@Test
	void testFilterLoadsUserFromToken() throws ServletException, IOException {
		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
		when(jwtUtilTokenGenerator.extractUsername(any())).thenReturn("miw");
		when(jwtUtilTokenGenerator.validateToken(anyString(), any())).thenReturn(true);
		
		filterToTest.doFilterInternal(mockRequest, mockResponse, mockFilterChain);
		verify(userDetailsService, times(1)).loadUserByUsername(userDetails.getUsername());
	}

	@Test
	void testFilterSetAuthenticationInSecurityContext() throws ServletException, IOException {
		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
		when(jwtUtilTokenGenerator.extractUsername(any())).thenReturn("miw");
		when(jwtUtilTokenGenerator.validateToken(anyString(), any())).thenReturn(true);
		
		filterToTest.doFilterInternal(mockRequest, mockResponse, mockFilterChain);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User principal = (User) authentication.getPrincipal();
		assertEquals(principal.getUsername(), "miw");
	}
}
