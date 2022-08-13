package com.miw.gildedrose.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.miw.gildedrose.domain.UserDao;
import com.miw.gildedrose.persistence.UserRepository;


@SpringBootTest
@ActiveProfiles("test")
public class BaseUserDetailsServiceMockTest {
	
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
	    public void invalidUsernameUserdetailsTest() throws Exception{

	        final String username = "test";	        
	        final UserDao user = createUserDao();
	        when(userRepository.findByUsername(username)).thenReturn(user);
	        
	        assertThrows(UsernameNotFoundException.class, () -> {
	        	userDetailsService.loadUserByUsername(username);
	        });	        
	    }	    
	    	    
	    private UserDao createUserDao() {
	    	UserDao user = new UserDao();
	    	user.setUsername("miw");
	    	user.setPassword("miw");	    	    	
	    	return user;	    	
	    }
}
