package com.example.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.model.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	Optional<MemberEntity> findByName(String name);
}
