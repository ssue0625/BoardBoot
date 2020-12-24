package com.example.web.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bno;
	private String bcomment;
	private String bcontent;
	private Date bdate;
	private String bpassword;
	private String btitle;
	private String bwriter;
	private int hitcount;
}
