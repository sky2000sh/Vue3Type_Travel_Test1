package com.vue3type_travel.web.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vue3type_travel.web.dao.MemberAccountDao;
import com.vue3type_travel.web.entity.Member;
import com.vue3type_travel.web.entity.MemberInfo;
import com.vue3type_travel.web.repository.MemberRepository;
import com.vue3type_travel.web.service.JwtService;

import io.jsonwebtoken.Claims;

@RestController
public class AccountController {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	MemberAccountDao memberAccountDao;
	
	// 해당 메서드의 리턴되는 Object를 IoC로 등록해준다.
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 스프링에서 비동기 처리를 하는 경우 @RequestBody 와 @ResponseBody 를 사용
	
	// @RequestBody
	// http요청의 body 부분을 java 객체로 받게 해서 객체에 저장할 수 있게 해주는 어노테이션. 주로 json을 받을 때 활용
	// = 클라이언트가 전송하는 Json(application/json) 형태의 HTTP Body를 Java 객체로 변환시켜주는 역할
	// =기본 생성자로 객체를 만들고, Getter나 Setter 등의 메소드로 필드를 찾아 Reflection으로 값을 설정
	
	// @ResponseBody
	// 서버에서 클라이언트로 응답 데이터를 전송하기 위해서 @ResponseBody 를 사용하여
	// 자바 객체를 HTTP 응답 본문의 객체로 변환하여 클라이언트로 전송시키는 역할
	
	// @RequetParam
	// 1개의 HTTP 파라미터를 얻기 위해 사용되며 기본값을 지정
	// 필수 여부가 true이기 때문에 반드시 필요한 경우가 아니라면 required=false 설정이 필요함
	// 데이터를 받을때는 데이터를 저장하는 이름으로 메서드의 변수명을 설정
	
	// @RequestMapping
	// 컨트롤러를 선택할 때 대표적으로 사용하는 애노테이션이다.
	// url당 하나의 컨트롤러에 매핑되던 다른 핸들러 매핑과 달리 메서드 단위까지 세분화하여 적용할 수 있으며,
	// url 뿐 아니라 파라미터, 헤더 등 더욱 넓은 범위를 적용할 수 있다.
	// get방식과 post방식이 있다.
	
	// @PathVariable
	// @PostMapping("/api/cart/places/{placeId}")
	// (@PathVariable(name = "placeId") 또는 @PathVariable("placeId")
	// {}를 활용해 변수처럼 적어준 뒤 @PathVariable 어노테이션 뒤에 {} 안에 적은 변수 명을 name 속성의 값으로 넣는다.
	// 그 후 이를 받을 자료형과 변수명을 옆에 선언해주면 잘 작동한다. rest api에서 많이 쓰인다.
	
	// 아래 post로 데이터를 요청받는데, 인자값을 param으로 받고 그 안에 email, password를 던져서 id 값을 리턴해준다.
	@PostMapping("/api/account/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> param, HttpServletResponse res) {

		Member member = new Member();
		
		// 암호화 된 비밀번호를 email를 통해 DB에서 가져오기
		String password = memberAccountDao.getMemberEmailFromPwd(param.get("email"));
		// 받아온 password 값을 matches를 이용해 입력된 password와 비교
		boolean result =  bCryptPasswordEncoder.matches(param.get("password"), password);
		
		if(result) {
			// findByEmailAndPassword에 암호화 된 password를 넣어주어 총 param.email과 그 password를 담아준다
			member = memberRepository.findByEmailAndPassword(param.get("email"), password);
		}
		// result 값이 아니라면 email / password값을 프론트에 내놓아 프론트에서 입력한 정보가 없다고 알림창주기
//		else {
//			return param.get("email");
//		}
		
		// member값이 null이 아니면, id값을 토큰화해서 토큰을 쿠키에 넣고 응답값에 추가를 하고, 리턴해준다.		
		if(member != null) {
			// JwtService jwtService = new JwtServiceImpl();
			int id = member.getId();
			String token = jwtService.getToken("id", id);
			
			Cookie cookie = new Cookie("token", token);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			
			res.addCookie(cookie);
			
			// return ResponseEntity.ok().build();  // 응답값으로 토큰 임의생성값을 리턴하게끔 해준다.
			return new ResponseEntity<>(id, HttpStatus.OK);  // 응답값으로 id값으로 반환 리턴하게끔 해준다.		
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/api/account/logout")
	public ResponseEntity<?> logout(HttpServletResponse res) {
		
		Cookie cookie = new Cookie("token", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		
		res.addCookie(cookie);
		
		return new ResponseEntity<>(HttpStatus.OK);		
	}
	
	@GetMapping("/api/account/check")
	public ResponseEntity<?> check(@CookieValue(value = "token", required = false) String token) {
		Claims claims = jwtService.getClaims(token);
		
		if(claims != null) {
			int id = Integer.parseInt(claims.get("id").toString());
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);		
	}
	
	@Transactional
	@PostMapping("/api/joinProc")
	public ResponseEntity<?> joinProc(@RequestBody Map<String, String> param, HttpServletResponse res) throws Exception {

		Member member = new Member();
		MemberInfo memberInfo= new MemberInfo();
				
		// 이메일 저장
		member.setEmail(param.get("email"));
		
		// 비밀번호 저장
		String rawPassword = param.get("password");
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		member.setPassword(encPassword);
		
		// 유저 이름 저장
		member.setMemberName(param.get("memberName"));
		
		// 유저 정보 저장
		memberInfo.setEmail(param.get("email"));
		memberInfo.setPhone(param.get("phone"));
		memberInfo.setBirth(param.get("birth"));		
		memberInfo.setGender(param.get("gender"));
		memberInfo.setAddress1(param.get("address1"));
		memberInfo.setAddress2(param.get("address2"));
		
		//memberRepository.save(member);
		memberAccountDao.memberSignUp(member);
		memberAccountDao.memberInfoSignUp(memberInfo);
		
		String memberName = member.getMemberName();
		
		return new ResponseEntity<>(memberName, HttpStatus.OK);
	}

}
