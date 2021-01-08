package com.example.web.dto;

import com.example.web.model.MemberEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

	private Long id;
	private String name;
	private String password;

	public MemberEntity toEntity() {
		return new MemberEntity(id, name, password);
	}
}
