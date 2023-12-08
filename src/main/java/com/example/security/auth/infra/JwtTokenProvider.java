package com.example.security.auth.infra;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.security.auth.domain.TokenProvider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider implements TokenProvider {

	private final String secretKey;

	private final long expirationHours;

	private final String issuer;

	public JwtTokenProvider(
		@Value("${security.secret-key}") final String secretKey,
		@Value("${security.expiration-hours}") final long expirationHours,
		@Value("${security.issuer}") final String issuer
	) {
		this.secretKey = secretKey;
		this.expirationHours = expirationHours;
		this.issuer = issuer;
	}

	@Override
	public String createToken(final String userSpecification) {
		return Jwts.builder()
			.setSubject(userSpecification)
			.setIssuer(issuer)
			.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
			.setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))
			.signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
			.compact();
	}

	@Override
	public String validateTokenAndGetSubject(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey.getBytes())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
