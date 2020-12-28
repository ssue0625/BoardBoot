package com.example.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.dto.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
