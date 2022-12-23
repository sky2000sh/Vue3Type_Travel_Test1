package com.vue3type_travel.web.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("JwtService")
public class JwtServiceImpl implements JwtService {
	
	private String secretKey = "aiekdivnbowmfjvifmdjwhdkvn922u4i5#$%kdeji34mekfoivk32%^&&*dkdkdu1j@#";

	@Override
	public String getToken(String key, Object value) {
		
		// 아래 명령어들은 입력받은 key와 value를 secretKey이용해서 만들어주는 서비스 메서드		
		Date expTime = new Date();
		expTime.setTime(expTime.getTime() + 1000 * 60 * 30);  // 5 => 5분 / 30 => 30분
		byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
		Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
		
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("typ", "JWT");
		headerMap.put("alg", "HS256");
		
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		
		JwtBuilder builder = Jwts.builder().setHeader(headerMap)
				.setClaims(map)
				.setExpiration(expTime)
				.signWith(signKey, SignatureAlgorithm.HS256);

		return builder.compact();
	}

	@Override
	public Claims getClaims(String token) {
		
		if(token != null && !"".equals(token)) {
			try {
				byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
				Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
				
				// Claims claims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
				// return claims;
				return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
			} catch(ExpiredJwtException e) {
				// 만료되었을 때
				
			} catch(JwtException e) {
				// 유효하지 않을 때
				
			}
		}
		return null;
	}

	@Override
	public boolean isValid(String token) {		
		return this.getClaims(token) != null;
	}

	@Override
	public int getId(String token) {
		Claims claims = this.getClaims(token);
		
		if(claims != null) {
			return Integer.parseInt(claims.get("id").toString());
		}
				
		return 0;
	}

}

