package com.example.web.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bno;
	private String bcomment;
	private String bcontent;
	@Temporal(TemporalType.DATE)
	@Column(insertable = true, updatable = false)
	private Date bdate;
	@NotNull
	private String bpassword;
	private String btitle;
	private String bwriter;
	private int hitcount;

	@PrePersist
	private void onCreate() {
		this.bdate = new Date();
	}
}
