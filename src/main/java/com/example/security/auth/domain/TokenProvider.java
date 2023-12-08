package com.example.security.auth.domain;

public interface TokenProvider {

	String createToken(String userSpecification);

	String validateTokenAndGetSubject(String token);
}
