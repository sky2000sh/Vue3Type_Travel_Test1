package com.vue3type_travel.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		// setAllowCredentials => 내 서버가 응답을 할 때 
		// json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
		// false 시, 자바스크립트에서 요청을 했을 때 응답이 오지 않는다.
		config.setAllowCredentials(true);
		
		// addAllowedOrigin => 모든 ip에 응답을 허용하겠다.
		// config.addAllowedOrigin("*"); // e.g. http://domain1.com
		
		config.addAllowedOriginPattern("*");
		
		// addAllowedHeader => 모든 header에 응답을 허용하겠다.
		config.addAllowedHeader("*");
		// addAllowedMethod => 모든 post, get, put, delete, patch 요청을 허용하겠다.
		config.addAllowedMethod("*");
		
		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(source);
	}

}
