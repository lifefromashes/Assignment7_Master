package com.merit.assignment7.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merit.assignment7.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);

}
