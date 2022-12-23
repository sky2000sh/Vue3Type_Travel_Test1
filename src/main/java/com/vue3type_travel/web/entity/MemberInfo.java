package com.vue3type_travel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//ORM - Object Relation Mapping

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "member_info")
public class MemberInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 50, nullable = false, unique = true)
	private String email;
	
	@Column(length = 20, nullable = false)
	private String phone;
	
	@Column(nullable = true)
	private String birth;
	
	@Column(length = 8, nullable = true)
	private String gender;
	
	@Column(length = 255, nullable = true)
	private String address1;
	
	@Column(length = 255, nullable = true)
	private String address2;

}
