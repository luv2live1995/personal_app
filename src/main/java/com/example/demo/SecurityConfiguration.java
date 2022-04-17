package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.MyUserDetailsService;

@Configuration
public class SecurityConfiguration {
	
	@Configuration
	@Order(1)
	@EnableWebSecurity
	public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired 
		private BCryptPasswordEncoder bCryptPasswordEncoder;
				
	    @Autowired
	    private MyUserDetailsService adminDetailsService;
	    
	    @Autowired
	    private DataSource dataSource;
	    
	    private final String USERS_QUERY = "SELECT email, password, active from users where email=?";
	    private final String ROLES_QUERY = "SELECT u.email, r.role from users u inner join user_role ur on (u.id == ur.user_id) inner join role r on (ur.role_id = r.role_id) where u.email=?";
	    
	    
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
//			auth.userDetailsService(adminDetailsService)
//				.passwordEncoder(bCryptPasswordEncoder);
			auth.jdbcAuthentication()
				.usersByUsernameQuery(USERS_QUERY)
				.authoritiesByUsernameQuery(ROLES_QUERY)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
		}
		
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
		        	.mvcMatchers("/", "/admin/login", "admin/home", "/admin/registration", "/error").permitAll() // url that doesn't require auth
//					.mvcMatchers("/", "/js/**", "/css/**", "/bootstrap-4.0.0-dist/css/**", "/bootstrap-4.0.0-dist/js/**").permitAll()
//		        	.mvcMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
		        	.mvcMatchers("/admin/home").hasAuthority("ADMIN").anyRequest()
		        	.authenticated().and()
//		        	.csrf().disable()
		        	.requestCache().disable() // For ex, user tended to go to /help URL in logout status, then he login, he is supposed to go to /help page. This is disabled
		        .formLogin()
		        	.loginPage("/admin/login").loginProcessingUrl("/admin/login")
		        	.defaultSuccessUrl("/admin/home")		// Default success page after login successfully
		        	.failureUrl("/admin/login?error=true")		// Default failure page after login failed
		        	.usernameParameter("username")			// name="username" attribute getting from form
		        	.passwordParameter("password")			// name="password" attribute getting from form
		        	.and()
		        .logout()
		        	.logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
		        	.logoutSuccessUrl("/admin/login")
		        	.and()
		        	.exceptionHandling()
		        	.accessDeniedPage("/access-denied");
		}
		
		
		/**
		 * Ignore any requests to static resources
		 * https://stackoverflow.com/questions/29420037/after-spring-security-login-im-redirected-to-a-css-js-resource-instead-of-a-ht
		 */
		@Override
		public void configure(WebSecurity web) throws Exception { 
			web
				.ignoring() 
				.antMatchers("/images/**", "/css/**", "/bootstrap-4.0.0-dist/**", "/jquery-3.6.0/**", "/js/**"); 
		}
		 
		
	}
	
	@Configuration
	@Order(2)
	@EnableWebSecurity
	public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired 
		private BCryptPasswordEncoder bCryptPasswordEncoder;
				
	    @Autowired
	    private MyUserDetailsService userDetailsService;
	    
		
//		@Bean
//		public PasswordEncoder passwordEncoder() { 
//			return new BCryptPasswordEncoder(); 
//		}
		
	    
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
			auth.userDetailsService(userDetailsService)
				//
				.passwordEncoder(bCryptPasswordEncoder); 
		}
		
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
		        	.mvcMatchers("/", "/user/login", "user/home", "/user/registration", "/error").permitAll() // url that doesn't require auth
//					.mvcMatchers("/", "/js/**", "/css/**", "/bootstrap-4.0.0-dist/css/**", "/bootstrap-4.0.0-dist/js/**").permitAll()
//		        	.mvcMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
		        	.mvcMatchers("/user/home").hasAuthority("USER").anyRequest()
		        	.authenticated().and()
		        	.requestCache().disable() // For ex, user tended to go to /help URL in logout status, then he login, he is supposed to go to /help page. This is disabled
		        .formLogin()
		        	.loginPage("/login")
		        	.defaultSuccessUrl("/user/home")		// Default success page after login successfully
		        	.failureUrl("/user/login?error=true")		// Default failure page after login failed
		        	.usernameParameter("username")			// name="username" attribute getting from form
		        	.passwordParameter("password")			// name="password" attribute getting from form
		        	.and()
		        .logout()
		        	.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		        	.logoutSuccessUrl("/user/login")
		        	.and()
		        	.exceptionHandling()
		        	.accessDeniedPage("/access-denied");
		}
		
		
		/**
		 * Ignore any requests to static resources
		 * https://stackoverflow.com/questions/29420037/after-spring-security-login-im-redirected-to-a-css-js-resource-instead-of-a-ht
		 */
		@Override
		public void configure(WebSecurity web) throws Exception { 
			web
				.ignoring() 
				.antMatchers("/images/**", "/css/**", "/bootstrap-4.0.0-dist/**", "/jquery-3.6.0/**", "/js/**"); 
		}
		 
		
	}
	
}



