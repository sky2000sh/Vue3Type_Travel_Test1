package com.vue3type_travel.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vue3type_travel.web.config.CorsConfig;
import com.vue3type_travel.web.config.jwt.JwtAuthenticationFilter;
import com.vue3type_travel.web.config.jwt.JwtAuthorizationFilter;
import com.vue3type_travel.web.repository.MemberRepository;

/* SecurityConfig 파일을 생성하고 명령어들을 입력하면
 * 기본적으로 시큐리티에서 제공하는 로그인화면이 없어지고
 * 사용자가 만들어내는 뷰 단의 로그인 화면으로 이동되어
 * 실질적으로 권한이 있는 사용자를 가려내서 사용할 수 있게 된다. */
@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션 => 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	//private CorsFilter corsFilter;
	private CorsConfig corsConfig;
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.addFilter(corsConfig.corsFilter())
			.csrf().disable()
			// 아래 sessionManagement()은 세션을 사용하지 않겠다는 의미
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

		.and()
			// @CrossOrigin => 인증(X), 시큐리티 필터에 등록 인증(O)
			//.addFilter(corsFilter)  // 이 필터를 타야 CorsConfig의 모든 요청을 받아낼 수 있다.
			
			// 아래 formLogin()은 기존에 행하던 formLogin 절차를 없애겠다는 의미
			.formLogin().disable()
			// 
			.httpBasic().disable()
			
			// 아래 authenticationManager객체는 WebSecurityConfigurerAdapter가 가지고 있다.
			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository))
			.authorizeRequests()
			// ===== 여기서부터 아래까지는 하고자하는 프로젝트&서버에 따라 경로가 달라질 수 있음 =====
			.antMatchers("/api/v1/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
				.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
				.access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();
			// ===== 여기까지 =====
		
		// 아래 authorizeRequests() 이하는 Spring Security일 경우에 활성화 가능
			/*
			.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll() */
		
		// 아래 formLogin() 이하는 ONLY Spring Security일 경우에만 활성화 가능
		/*
		.and()
			.formLogin()
			.loginPage("/login")
			//.usernameParameter(변수 username이 아닐 시, 본인이 생성한 임의 유저 변수(DB에 있는 변수명도 괜찮음))
			//.passwordParameter(변수 password가 아닐 시, 본인이 생성한 임의 비번 변수(DB에 있는 변수명도 괜찮음))
			.loginProcessingUrl("/loginProc")
			.defaultSuccessUrl("/") */
		
	}
}
