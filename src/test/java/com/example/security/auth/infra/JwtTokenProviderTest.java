package com.example.security.auth.infra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.security.SignatureException;

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

	@Test
	@DisplayName("토큰 파싱 테스트")
	void parse() {
		// given
		String userSpecification = "member:ADMIN";
		String token = tokenProvider.createToken(userSpecification);

		// when
		String result = tokenProvider.validateTokenAndGetSubject(token);

		// then
		Assertions.assertThat(result).isEqualTo(userSpecification);
	}

	@Test
	@DisplayName("잘못된 토큰 파싱 테스트")
	void parseInvalidToken() {
		// given
		String token =
			"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ3b293YSIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6OTk5OTk5OTk5OX0.-5yUrTOn4Zg0uqy_iWrToXqpE1-WhYp2K3YYxAssSoA";

		// when, then
		Assertions.assertThatThrownBy(() -> tokenProvider.validateTokenAndGetSubject(token))
			.isInstanceOf(SignatureException.class);
	}
}
