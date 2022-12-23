package com.vue3type_travel.web.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//ORM - Object Relation Mapping

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 50, nullable = false, unique = true)
	private String email;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 100, nullable = true)
	private String role;
	
	@Column(length = 100, nullable = true)
	private String memberName;
	
	// role이 한 개, 즉 user면 user / admin이면 admin / manager면 manager
	// 이렇게 한 개라면 아래 List<String> getRoleList() 메서드를 만들 필요가 없다.
	// => 한 개의 user가 아이디가 2개 이상일 때 등등 아래의 메서드를 사용한다.
	// ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    public List<String> getRoleList() {
    	if(this.role.length() > 0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

}
