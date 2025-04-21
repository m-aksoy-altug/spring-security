package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.Users;

public interface UserRepo extends JpaRepository<Users, String> {

}
