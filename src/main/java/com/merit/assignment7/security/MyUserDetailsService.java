package com.merit.assignment7.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.merit.assignment7.model.MyUserDetails;
import com.merit.assignment7.model.User;
import com.merit.assignment7.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(username);

		user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
		return user.map(MyUserDetails::new).get();
	}
}
