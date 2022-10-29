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
@Table(name = "PLACES")
public class Place {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@Column(length = 100)
	private String imgPath;
	
	@Column
	private int price;
	
	@Column
	private int discountPer;

}
