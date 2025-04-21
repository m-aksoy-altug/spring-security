package com.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired 
	private BCryptPasswordEncoder encoder; 
	
	@Autowired
	private DataSource dataSource; 
	
	@Autowired
	private PersistentTokenRepository tokenRepo; 
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(encoder)
		.usersByUsernameQuery("select username,password,enabled from users where username=?")
		.authoritiesByUsernameQuery("select username,role from user_roles where username=?");
	}
	
//	@Bean
//	public CsrfTokenRepository csrfTokenRepository() {
//		HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
//		repo.setHeaderName("X-XSRF-TOKEN");
//		return repo;
//	}
 	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http,HandlerMappingIntrospector introspector) throws Exception {
		
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector).servletPath("/**");
		
		http.csrf((csrf)-> 
			csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
        
        http.requiresChannel((requiresChannel)-> requiresChannel.anyRequest().requiresSecure());
//        http.rememberMe((rememberMe)-> rememberMe.tokenRepository(tokenRepo).tokenValiditySeconds(86400));
       
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                  authorizationManagerRequestMatcherRegistry
                  .requestMatchers(new AntPathRequestMatcher("/WEB-INF/pages/**")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/doRegister")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/login")).permitAll());

        http.authorizeHttpRequests((authorize)-> authorize        
        .requestMatchers(mvcMatcherBuilder.pattern("/**")).hasAnyRole("USER","ADMIN")
        .anyRequest().fullyAuthenticated());

        http.formLogin((formLogin)-> formLogin
        		.loginPage("/login")
        		.usernameParameter("username")
        		.passwordParameter("password")
        		.loginProcessingUrl("/doLogin")
        		.defaultSuccessUrl("/index",true)
        		.failureUrl("/accessDenied")
        		.permitAll());
        
        http.exceptionHandling(exceptionHandling -> 
        		exceptionHandling.accessDeniedPage("/accessdenied"));
        
        http.logout((logout)-> logout
        		.logoutUrl("/logout")
        		.logoutSuccessUrl("/login")
        		.deleteCookies("JSESSIONID")
        		.permitAll());
        
        http.sessionManagement(session -> session.invalidSessionUrl("/invalidSession").maximumSessions(1));
        
        return http.build();
    }
		
	public void addResourceHandler(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("/classpath:/static/images/**");	
	}

	
//	@Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user1 = User.withUsername("user1")
//	            .password(passwordEncoder().encode("user11"))
//	            .roles("USER")
//	            .build();
//	        UserDetails user2 = User.withUsername("user2")
//	            .password(passwordEncoder().encode("user22"))
//	            .roles("USER")
//	            .build();
//	        UserDetails admin = User.withUsername("admin")
//	            .password(passwordEncoder().encode("admin111"))
//	            .roles("ADMIN")
//	            .build();
//	        return new InMemoryUserDetailsManager(user1, user2, admin);
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    	 http.csrf()
//         .disable()
//         .authorizeRequests()
//         .requestMatchers("/admin/**")
////         .antMatchers("/admin/**")
//         .hasRole("ADMIN")
////         .antMatchers("/anonymous*")
//         .requestMatchers("/anonymous*")
//         .anonymous()
////         .antMatchers("/login*")
//         .requestMatchers("/login*")
//         .permitAll()
//         .anyRequest()
//         .authenticated()
//         .and()
//          .formLogin();
////          .loginPage("/login.html")
////          .loginProcessingUrl("/perform_login")
////          .defaultSuccessUrl("/homepage.html", true)
////          .failureUrl("/login.html?error=true")
////          .failureHandler(authenticationFailureHandler())
////          .and()
////          .logout()
////          .logoutUrl("/perform_logout")
////          .deleteCookies("JSESSIONID")
////          .logoutSuccessHandler(logoutSuccessHandler());
//          return http.build();
//    }
    
    
    
    	

}
