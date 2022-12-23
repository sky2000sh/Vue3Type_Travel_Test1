package com.vue3type_travel.web.dto;

import com.vue3type_travel.web.dto.LoginRequestDto;
import lombok.Data;

@Data
public class LoginRequestDto {
	
	private String email;
	private String password;
}
