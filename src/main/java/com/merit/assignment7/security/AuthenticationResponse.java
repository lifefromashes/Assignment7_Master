package com.merit.assignment7.security;

public class AuthenticationResponse {

private final String JWT;
	
	public AuthenticationResponse(String JWT) {
		this.JWT = JWT;
	}
	
	public String getJWT() {
		return JWT;
	}
}
