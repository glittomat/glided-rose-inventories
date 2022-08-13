package com.miw.gildedrose.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.miw.gildedrose.service.JwtUserDetailsService;

/**
 * The security configure class.
 *
 */
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	/** The JwtUserDetailsService. */
	@Autowired
	private JwtUserDetailsService userDetailsService;

	/** The JwtRequestFilter. */
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/** The Accessible URLS. */
	private static final String[] ACCESS_URLS = { "/webjars/**", "/api-docs.yaml", "/swagger-ui.html", "/swagger-ui/**",
			"/api-docs/**", "/authenticate" };

	/**
	 * Overridden method where the AuthenticationManagerBuilder is used to specify
	 * the AuthenticationManager.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		super.configure(auth);
	}

	/**
	 * The method to configure httpSecurity.
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		// For h2 console enabling
		httpSecurity.authorizeRequests().antMatchers("/h2/**").permitAll();
		
		httpSecurity.csrf().disable().cors().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(ACCESS_URLS).permitAll().anyRequest().authenticated().and().exceptionHandling().and()
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.headers().frameOptions().disable();
		
	}

	
	/**
	 * Get the AuthenticationManager Bean
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Enabling password encoder.
	 * 
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
