package com.vue3type_travel.web.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vue3type_travel.web.config.auth.PrincipalDetails;
import com.vue3type_travel.web.config.jwt.JwtProperties;
import com.vue3type_travel.web.dto.LoginRequestDto;

import lombok.RequiredArgsConstructor;

//spring security에서 UsernamePasswordAuthenticationFilter가 있다.
///login 요청해서 username, password를 post로 전송하면 UsernamePasswordAuthenticationFilter가 동작은 한다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
private final AuthenticationManager authenticationManager;
	
	// Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
	// 인증 요청시에 실행되는 함수 => /login
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("JwtAuthenticationFilter : 진입");
		
		/* 1. username, password를 받아
		 * 2. 정상인지 로그인 시도를 하기.
		 * ==> authenticationManager로 로그인 시도를 하면 principalDetailisService가 호출되어
		 * 		그 안에 있는 loadUserByUsername() 메서드가 실행된다.
		 * 		메서드 실행이 정상이면 authentication이 리턴된다.
		 * 		이는, DB에 있는 username과 password가 일치한다는 의미.
		 * 3. principalDetailis을 세션에 담고 (= 권한 관리를 위해서)
		 * 4. JWT 토큰을 만들어서 응답하면 된다.
		 */
		
		// request에 있는 username과 password를 파싱해서 자바 Object로 받기
		ObjectMapper om = new ObjectMapper();
		LoginRequestDto loginRequestDto = null;
		try {
			loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("JwtAuthenticationFilter : " + loginRequestDto);
		
		// 유저네임패스워드 토큰(Token) 생성
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(
						loginRequestDto.getEmail(), 
						loginRequestDto.getPassword());
		
		System.out.println("JwtAuthenticationFilter : 토큰생성완료");
		
		// 위의 2번에 해당됨
		// authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
		// loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
		// UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.
		
		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
		// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
		Authentication authentication = 
				authenticationManager.authenticate(authenticationToken);
		// 위의 명령어는 로그인 정보가 담겨져있다.
		
		// 그렇게 되면 로그인이 되었다는 의미
		PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("Authentication 로그인 완료 : " + principalDetailis.getMember().getMemberName());
		
		// 아래 authentication객체가 session 영역에 저장해야하고 그 방법이 return으로 해주면 된다.
		// ruturn의 이유는 권한 관리를 security가 대신 해주기 때문에 편하게 하기 위해 하는 것.
		// 굳이 JWT 토큰을 사용하면서 세션을 만들 이유는 없음. 하지만 단지 권한 처리 때문에 session에 넣어준다.
		return authentication;
	}

	// attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 메서드가 실행된다.
	// JWT Token 생성해서, request 요청한 사용자에게 JWT Token를 response에 담아주기
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
		
		// RSA방식 대신, HMAC512(HashMAC512암호화)방식
		String jwtToken = JWT.create()
				// 아래는 토큰 이름, 주제, 제목
				.withSubject(principalDetailis.getUsername())  // 
				// 아래는 토큰 만료시간 (시간은 짧게)
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 60000 * 10 = 10분
				// withClaim => 본인이 넣고싶은 key, value 아무거나 넣어도 된다.
				.withClaim("id", principalDetailis.getMember().getId())
				.withClaim("memberName", principalDetailis.getMember().getMemberName())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
	}

}
