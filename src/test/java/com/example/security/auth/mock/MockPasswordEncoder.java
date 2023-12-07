package com.example.security.auth.mock;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MockPasswordEncoder implements PasswordEncoder {

	public static final String MOCK_PASSWORD = "hello";

	@Override
	public String encode(CharSequence rawPassword) {
		return MOCK_PASSWORD;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return false;
	}

	@Override
	public boolean upgradeEncoding(String encodedPassword) {
		return PasswordEncoder.super.upgradeEncoding(encodedPassword);
	}
}
