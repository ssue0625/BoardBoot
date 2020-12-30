package com.example.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.Board;
import com.example.web.repository.BoardRepository;

@RestController
@RequestMapping("/api")
class BoardApiController {

	@Autowired
	private BoardRepository repository;

	// Aggregate root
	// tag::get-aggregate-root[]
//	@GetMapping("/boards")
//	List<Board> all() {
//		return repository.findAll();
//	}
	// end::get-aggregate-root[]

	@GetMapping("/boards")
	List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
			@RequestParam(required = false, defaultValue = "") String content) {
		if (!StringUtils.hasText(title) && !StringUtils.hasText(content)) {
			return repository.findAll();
		} else {
			return repository.findByBtitleOrBcontent(title, content);
		}
	}

	@PostMapping("/boards")
	Board newBoard(@RequestBody Board newBoard) {
		return repository.save(newBoard);
	}

	// Single item

	@GetMapping("/boards/{bno}")
	Board one(@PathVariable Long bno) {
		return repository.findById(bno).orElse(null);
	}

	@PutMapping("/boards/{bno}")
	Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long bno) {
		return repository.findById(bno).map(board -> {
			board.setBwriter(newBoard.getBwriter());
			board.setBtitle(newBoard.getBtitle());
			board.setBcontent(newBoard.getBcontent());
			board.setBdate(newBoard.getBdate());
			board.setBpassword(newBoard.getBpassword());
			return repository.save(board);
		}).orElseGet(() -> {
			newBoard.setBno(bno);
			return repository.save(newBoard);
		});
	}

	@DeleteMapping("/boards/{bno}")
	void deleteBoard(@PathVariable Long bno) {
		repository.deleteById(bno);
	}
}