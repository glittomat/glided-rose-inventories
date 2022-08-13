package com.miw.gildedrose.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.core.userdetails.User;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miw.gildedrose.domain.UserDao;
import com.miw.gildedrose.domain.messaging.AuthenticationRequest;
import com.miw.gildedrose.service.JwtUserDetailsService;
import com.miw.gildedrose.util.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthenticationControllerMockTest {

	@Autowired
    private WebApplicationContext context;

	 private static ObjectMapper mapper = new ObjectMapper();
	 
    private MockMvc mockMvc;
    
    @Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtil jwtTokenUtil;

	@Mock
	private JwtUserDetailsService userDetailsService;

    @BeforeEach
    public void setup() {
    	mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    	
	@Test
	public void existentUserTokenAndAuthenticationTest() throws Exception {
    
	   UserDao user =  createUserDao();
	   AuthenticationRequest request = new AuthenticationRequest(user.getUsername(), user.getPassword());
	   when(userDetailsService.loadUserByUsername(any())).thenReturn(new User(user.getUsername(), user.getPassword(), new ArrayList<>()));
	   
	   MvcResult result = mockMvc.perform(post("/authenticate")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(mapper.writeValueAsString(request))
			   .accept(MediaType.APPLICATION_JSON))
	           .andExpect(status().isOk()).andReturn();  	   
	    
	   String response = result.getResponse().getContentAsString();
	   assertTrue(response.contains("jwt"));
	}
	
	@Test
	public void nonExistentUserTokenAndAuthenticationTest() throws Exception {
    
	   UserDao user =  createUserDao();
	   AuthenticationRequest request = new AuthenticationRequest("test", "test");
	   when(userDetailsService.loadUserByUsername(any())).thenReturn(new User(user.getUsername(), user.getPassword(), new ArrayList<>()));
	   
	   mockMvc.perform(post("/authenticate")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(mapper.writeValueAsString(request))
			   .accept(MediaType.APPLICATION_JSON))
	           .andExpect(status().is5xxServerError());  	   	    	  
	}
	
	private UserDao createUserDao() {
	    UserDao user = new UserDao();
	    user.setUsername("miw");
	    user.setPassword("miw");	    	    	
	    return user;	    	
	}
}
