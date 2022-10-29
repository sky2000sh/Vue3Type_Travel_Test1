package com.vue3type_travel.web.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
	
	//public
		String getToken(String key, Object value);
		
		Claims getClaims(String token);
		
		// 인자로 받은 토큰이 문제가 없는지 여부 나타내기
		boolean isValid(String token);
		
		// 토큰 정보에서 사용자 정보를 가져오는 getId를 추가
		int getId(String token);
}
