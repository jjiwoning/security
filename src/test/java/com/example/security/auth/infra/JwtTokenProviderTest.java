package com.example.security.auth.infra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

	private JwtTokenProvider tokenProvider = new JwtTokenProvider(
		"asdabsjassadfajkwfdbqawdqawdasdas", 3, "test");

	@Test
	@DisplayName("토큰 발급 테스트")
	void create() {
		// given
		String userSpecification = "member:ADMIN";

		// when
		String token = tokenProvider.createToken(userSpecification);

		// then
		Assertions.assertThat(token.split("\\.")).hasSize(3);
	}
}
