package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.MyUserDetailsService;

/**
 * @author user
 *
 */
/**
 * @author user
 *
 */
/**
 * @author user
 *
 */
/**
 * @author user
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
    @Autowired
    private MyUserDetailsService userDetailsService;
    
	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 */
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder); 
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
	        	.mvcMatchers("/", "/login", "/registration", "/error").permitAll() // url that doesn't require auth
				.mvcMatchers("/", "/js/**", "/css/**", "/bootstrap-4.0.0-dist/css/**", "/bootstrap-4.0.0-dist/js/**").permitAll()
	        	.mvcMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
	        	.authenticated().and()
//	        .requestCache().disable() // For ex, user tended to go to /help URL in logout status, then he login, he is supposed to go to /help page. This is disabled
	        .formLogin()
	        	.loginPage("/login")
	        	.defaultSuccessUrl("/admin/home")
	        	.failureUrl("/login?error=true")
	        	.usernameParameter("username")
	        	.passwordParameter("password")
	        	.and()
	        .logout()
	        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        	.logoutSuccessUrl("/login")
	        	.and()
	        	.exceptionHandling()
	        	.accessDeniedPage("/access-denied");
	}
	
	
	/**
	 * Ignore any 
	 */
	@Override
	public void configure(WebSecurity web) throws Exception { 
		web
			.ignoring() 
			.antMatchers("/images/**", "/css/**"); 
	}
	 
	
}
