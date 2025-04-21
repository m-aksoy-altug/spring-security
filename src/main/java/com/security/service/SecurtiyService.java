package com.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.security.dto.UserDTO;
import com.security.entity.Users;
import com.security.entity.UserRoles;
import com.security.repo.UserRepo;
import com.security.repo.UserRolesRepo;


@Service
public class SecurtiyService {
	
	private static final Logger log = LoggerFactory.getLogger(SecurtiyService.class);
		
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserRolesRepo userRolesRepo;
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Transactional
	public UserDTO saveUserDetails(UserDTO userDTO) {
		String hashp= encoder.encode(userDTO.getPassword());
		System.out.println("Hashed pass:"+hashp);
		userDTO.setPassword(hashp);
		Users user= UserDTO.stoToEntityConverter(userDTO);
		user.setEnabled(true);
		user = userRepo.saveAndFlush(user);
		System.out.println("Usersaved "+user.toString());
		UserRoles userRoles = new UserRoles();
		userRoles.setRole("ROLE_USER");
		userRoles.setUsername(user.getUsername());
		userRolesRepo.saveAndFlush(userRoles);
		System.out.println("UserRoles saved "+userRoles.toString());
		return UserDTO.entityToTdoConverter(user);
	}
	
	
	@Transactional
	public JsonNode saveCredentials(JsonNode requestBody) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode responseNode = objectMapper.createObjectNode();
		String email = requestBody.get("email").asText();
//		User  userexist= userRepo.findByEmail(email);
//		log.info("userexist:: "+userexist);
//		if(userexist==null) {
//			User userSave= new User();
//			userSave.setEmail(email);
//			// implement salted hash
//			// SRP Verifier Secure Remote Password Protocol
//			userSave.setPassword(requestBody.get("password1").asText());
//			userSave.setPhone(requestBody.get("phone").asText());
//			userSave.setEmailVerified(false);
//			userSave.setFirstName(requestBody.get("firstName").asText());
//			userSave.setLastName(requestBody.get("lastName").asText());
////			userRepo.save(userSave);
//			log.info("Sending email TO :: "+email.trim());
//			sendEmail.sendEmailRegister(email.trim());			
//			return requestBody;
//		}
		return (JsonNode) responseNode;
	}
}
