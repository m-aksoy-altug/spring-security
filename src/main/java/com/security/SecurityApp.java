package com.security;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SecurityApp {
	// spring-boot:run
private static final Logger log= LoggerFactory.getLogger(SecurityApp.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
	@Bean 
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}
	
	
	@PostConstruct
	public void intProcess() {
		try {
			if(dataSource !=null) {
				JdbcTemplate template= new JdbcTemplate();
				template.setDataSource(dataSource);
			}else {
				throw new NullPointerException();
			}
			
		} catch (Exception e) {
	    	log.info("intProcess()"+e.getMessage());
			log.debug("intProcess()"+e.getMessage());
			log.error("intProcess()"+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApp.class,args);
	}
	


}
