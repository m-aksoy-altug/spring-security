package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.UserRoles;

public interface UserRolesRepo extends JpaRepository<UserRoles, Integer> {

}
