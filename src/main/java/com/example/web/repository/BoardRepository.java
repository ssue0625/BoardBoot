package com.example.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.dto.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	List<Board> findByBtitle(String title); // 제목으로 검색

	List<Board> findByBtitleOrBcontent(String title, String content); // 제목 && 내용 검색
}
