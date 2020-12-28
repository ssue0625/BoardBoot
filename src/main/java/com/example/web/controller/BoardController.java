package com.example.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.dto.Board;
import com.example.web.repository.BoardRepository;
import com.example.web.service.BoardService;
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
			return "writeBoardForm";
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
			model.addAttribute("board", board);
		}
		return "writeBoardForm";
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<Board> board = boardRepository.findAll();
		model.addAttribute("board", board);
		return "boardList";
	}

}
