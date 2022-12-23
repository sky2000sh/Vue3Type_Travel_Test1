package com.vue3type_travel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	
	@Column
	private int memberId;
	
	@Column
	private int placeId;
	
	@Column
	private int total_price;
	
	@Column
	private int adult_num;
	
	@Column
	private int kid_num;
	
	@Column
	private int baby_num;
	
	@Column(length = 50)
	private String name;
	
	@Column(length = 100)
	private String img_Path;
	
	@Column(nullable = false)
	private String date_from;
	
	@Column(nullable = false)
	private String date_to;
	
	@Column
	private String date_cart_put;
	
//	@Column
//	private List<Integer> list;
	
//	public List<Integer> getList() {
//		return list;
//	}
//	
//	public void set
	
}
