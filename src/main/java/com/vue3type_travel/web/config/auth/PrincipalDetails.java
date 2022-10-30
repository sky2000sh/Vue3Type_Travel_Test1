package com.vue3type_travel.web.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.vue3type_travel.web.config.auth.PrincipalDetails;
import com.vue3type_travel.web.entity.Member;
import lombok.Data;

/* 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 * 로그인 진행이 완료되면 시큐리티 session을 만들어준다. (= Security ContextHolder)
 * 이 때, 들어갈 수 있는 시큐리티는 Object 타입으로써, Authentification 타입 객체다.
 * Authentification 안에 User 정보가 있어야 한다.
 * User Object 타입 => UserDetails 타입 객체
 * Security session => Authentification => UserDetails(= PrincipalDetails)
 *  */

//Authentication 객체에 저장할 수 있는 유일한 타입
@Data
public class PrincipalDetails implements UserDetails {
	
	private static final long serialVersionUID = 2306285997857461240L;
	
	private Member member;
	
	// 매개변수 생성자
	public PrincipalDetails(Member member) {
		super();
		this.member = member;
	}
	
	@Override
	public String getPassword() {
		return member.getPassword();
	}
	
	@Override
	public String getUsername() {
		return member.getMemberName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
		
		/* !! 유효시간에 대한 예 !!
		우리 사이트, 1년 동안 회원이 로그인을 안하면, 휴면 계정으로 돌리기
		user.getLoginDate();
		현재시간 - 로그인 시간 = 1년을 초과하면 return fals;로 조정하는 방식으로. */
	}
	
	// 해당 User의 권한(= role, auth)을 리턴하는 곳
	// 아래는 spring security - jwt 합친것에 대한 @Override
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        member.getRoleList().forEach(r -> {
        	authorities.add(()->{ return r;});
        });
        return authorities;
	}

}
