package com.vue3type_travel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "MEMBER")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	
	@Column(length = 50, nullable = false, unique = true)
	private String email;
	
	@Column(length = 100, nullable = false)
	private String password;

}
