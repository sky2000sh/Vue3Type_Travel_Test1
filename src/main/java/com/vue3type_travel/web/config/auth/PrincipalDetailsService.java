package com.vue3type_travel.web.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vue3type_travel.web.repository.MemberRepository;
import com.vue3type_travel.web.entity.Member;

import lombok.RequiredArgsConstructor;

//http://localhost:8080/login => 이곳에서 동작을 하지 않는다.
//요청할 때 클래스 PrincipalDetailsService가 요청된다.

//아래는 spring security의 정석대로.
//시큐리티 설정에서 loginProcessingUrl("/login");
///login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername 함수가 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	// 0. Security session => Authentification => UserDetails(= PrincipalDetails)
	// 3. Security session => Authentification(내부 UserDetails)
	// 4. Security session(내부 Authentification(내부 UserDetails))로 속하게 된다.

//	public UserDetails loadMemberByEmailAndPassword(String email, String password) throws UsernameNotFoundException {
//		System.out.println("PrincipalDetailsService : 진입");
//		Member member = memberRepository.findByEmailAndPassword(email, password);
//
//		// session.setAttribute("loginUser", user);
//		return new PrincipalDetails(member);
//	}

	@Override
	public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입");
		Member member = memberRepository.findByMemberName(memberName);

		// session.setAttribute("loginUser", user);
		return new PrincipalDetails(member);
	}


}
