package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.web.dto.MemberDto;
import com.example.web.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/")
	public String index() {
		return "member/index";
	}

	@GetMapping("/user/signup")
	public String signup() {
		return "member/signup";
	}

	@PostMapping("/user/signup")
	public String signup(MemberDto memberDto) {
		memberService.save(memberDto);
		return "redirect:/user/login";
	}

	@GetMapping("/user/login")
	public String login() {
		return "member/login";
	}

	@GetMapping("/user/login/result")
	public String loginResult() {
		return "member/loginSuccess";
	}

	@GetMapping("/user/logout/result")
	public String logout() {
		return "member/logout";
	}

	@GetMapping("/user/info")
	public String myinfo() {
		return "member/myinfo";
	}

	@GetMapping("/admin")
	public String admin() {
		return "member/admin";
	}
}
