package com.example.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.web.dto.MemberDto;
import com.example.web.model.MemberEntity;
import com.example.web.model.Role;
import com.example.web.repository.MemberRepository;

@Service
public class MemberService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	@Transactional
	public Long save(MemberDto memberDto) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
		return memberRepository.save(memberDto.toEntity()).getId();
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Optional<MemberEntity> userEntityWrapper = memberRepository.findByName(name);
		MemberEntity userEntity = userEntityWrapper.orElse(null);
		List<GrantedAuthority> authorities = new ArrayList<>();

		if (("admin").equals(name)) {
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
		}

		return new User(userEntity.getName(), userEntity.getPassword(), authorities);
	}
}