package com.example.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.web.dto.Board;

@Component
public class BoardValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Board.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Board b = (Board) obj;
		if (!StringUtils.hasText(b.getBpassword())) {
			errors.rejectValue("bpassword", "key", "비밀번호를 입력해주세요.");
		}
	}

}
