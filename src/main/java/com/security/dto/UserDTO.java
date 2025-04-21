package com.security.dto;

import com.security.entity.Users;

public class UserDTO {
	
	private String username;
	private String password;
	private String city;
	private String phone;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + ", city=" + city + ", phone=" + phone + "]";
	}
	
	public static Users stoToEntityConverter(UserDTO userDTO) {
		Users user = new Users();
		user.setUsername(userDTO.getUsername());
		user.setCity(userDTO.getCity());
		user.setPassword(userDTO.getPassword());
		user.setPhone(userDTO.getPhone());
		return user;
	}
	
	public static UserDTO entityToTdoConverter(Users user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(user.getUsername());
		userDTO.setCity(user.getCity());
		userDTO.setPassword(user.getPassword());
		userDTO.setPhone(user.getPhone());
		return userDTO;
	}

}
