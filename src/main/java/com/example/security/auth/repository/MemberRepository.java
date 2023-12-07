package com.example.security.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.auth.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
