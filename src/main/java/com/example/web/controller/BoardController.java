package com.example.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.dto.Board;
import com.example.web.repository.BoardRepository;
import com.example.web.validator.BoardValidator;

@Controller
public class BoardController {

	@Autowired
	private BoardValidator BoardValidator;

	@Autowired
	private BoardRepository boardRepository;

	@PostMapping("/writeBoardForm")
	public String writeBoardForm(@Validated Board board, BindingResult bindingResult) {
		BoardValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
			return "board/writeBoardForm";
		}
		boardRepository.save(board);
		return "redirect:/list";
	}

	@GetMapping("/writeBoardForm")
	public String writeBoardForm(Model model, @RequestParam(required = false) Long bno) {
		if (bno == null) {
			model.addAttribute("board", new Board());
		} else {
			Board board = boardRepository.findById(bno).orElse(null);
			int hitcount = board.getHitcount();
			hitcount = hitcount + 1;
			board.setHitcount(hitcount);
			model.addAttribute("board", board);
			boardRepository.save(board); // 조회수 저장
		}
		return "board/writeBoardForm";
	}

	@GetMapping("/list")
	public String list(Model model, @PageableDefault(value = 20) Pageable pageable) {
		// List<Board> board = boardRepository.findAll();
		// boardRepository.findAll(PageRequest.of(page:조회할 페이지 번호, size:한 페이지 당 조회 갯수));
		Page<Board> board = boardRepository.findAll(pageable);

		int startPage = Math.max(1, board.getPageable().getPageNumber() - 10);
		int endPage = Math.min(board.getTotalPages(), board.getPageable().getPageNumber() + 10);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("board", board);
		return "board/boardList";
	}

}
