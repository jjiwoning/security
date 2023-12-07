package com.example.security.auth.domain;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security.auth.mock.MockPasswordEncoder;

class MemberTest {

	private final PasswordEncoder passwordEncoder = new MockPasswordEncoder();

	@Test
	void create() {
		// given
		String account = "hello";
		String password = "1234";
		String name = "tamtam";

		// when
		Member result = Member.of(account, password, name, passwordEncoder);

		// then
		assertThat(result).extracting(Member::getAccount)
			.isEqualTo(account);
		assertThat(result).extracting(Member::getPassword)
			.isEqualTo(MockPasswordEncoder.MOCK_PASSWORD);
		assertThat(result).extracting(Member::getName)
			.isEqualTo(name);
	}

	@Test
	void update() {
		// given
		String account = "hello";
		String password = "1234";
		String name = "tamtam";

		Member member = Member.of(account, password, name, passwordEncoder);

		// when
		member.updatePassword("123456", passwordEncoder);

		// then
		assertThat(member).extracting(Member::getPassword).isEqualTo(MockPasswordEncoder.MOCK_PASSWORD);
	}
}
