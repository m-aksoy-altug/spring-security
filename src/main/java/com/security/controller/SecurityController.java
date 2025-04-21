package com.security.controller;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.security.dto.UserDTO;
import com.security.service.SecurtiyService;

import jakarta.servlet.http.HttpServletRequest;


//@RestController
//@CrossOrigin
@Controller
public class SecurityController {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityController.class);
	
	@Autowired
	private SecurtiyService securtiyService;
	
	@GetMapping(value = "/")
	public String home() {
		return "index";
	}
	
	@GetMapping(value = "/index")
	public String index() {
		return "index";
	}
	
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(value = "/logout")
	public String logout() {
		return "login";
	}

	
	@GetMapping(value = "/register")
	public String regsiter() {
		return "register";
	}
	
	@PostMapping(value = "/doRegister")
	public String doRegister(@ModelAttribute UserDTO userDTO, HttpServletRequest request) {
		
		userDTO= securtiyService.saveUserDetails(userDTO);
		request.setAttribute("registering", userDTO);
		
		return "login";
	}
	
	@GetMapping(value = "/admin")
	public String admin(Model model) {
		model.addAttribute("admin", "Dummy Admin Name" );
		return "admin";
	}
	
//	@Secured("ROLE_ADMIN") //Method level security
	@GetMapping(value = "/report")
	public String report(Model model) {
		model.addAttribute("transaction", "get Report Detials" );
		return "report";
	}
	
	@PostMapping(value = "/report")
	public String generatereport(Model model) {
		Object sender = model.getAttribute("senderCity");
		Object receiver =  model.getAttribute("receiverCity");
		
		log.info("sender is {}.", sender);
		log.info("receiver is {}.", receiver);
		
		model.addAttribute("alltransactions", model);
		
		return "success";
	}
	
	@GetMapping(value = "/accessdenied")
	public String accees() {
		return "accessdenied";
	}
	
	@GetMapping(value = "/sessionExpired")
	public String sessionExpired( ) {
		return "sessionExpired";
	}
	
	@GetMapping(value = "/invalidSession")
	public String invalidSession( ) {
		return "invalidSession";
	}

}
