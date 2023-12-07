package com.example.security.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String account;

	@Column(nullable = false)
	private String password;

	private String name;

	@Enumerated(EnumType.STRING)
	private MemberRole role;

	public static Member of(
		final String account,
		final String password,
		final String name,
		final PasswordEncoder passwordEncoder
	) {
		return Member.builder()
			.account(account)
			.password(passwordEncoder.encode(password))
			.name(name)
			.build();
	}

	public void updatePassword(
		final String password,
		final PasswordEncoder passwordEncoder
	) {
		this.password = passwordEncoder.encode(password);
	}
}
