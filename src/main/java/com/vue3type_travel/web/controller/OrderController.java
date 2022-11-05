package com.vue3type_travel.web.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vue3type_travel.web.dto.OrderDto;
import com.vue3type_travel.web.entity.Order;
import com.vue3type_travel.web.repository.CartRepository;
import com.vue3type_travel.web.repository.OrderRepository;
import com.vue3type_travel.web.service.JwtService;

@RestController
public class OrderController {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("/api/orders")
	public ResponseEntity<Object> getOrder(@CookieValue(value="token", required = false) String token) {
		
		if(!jwtService.isValid(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		int memberId = jwtService.getId(token);
		List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);
		
		return new ResponseEntity<>(orders, HttpStatus.OK);	
	}

	/* 사용자는 /api/orders 쪽으로 order를 request하면
	 * 사용자가 입력한 내용을 dto를 담아서, 참조변수 newOrder라는 인스턴스를 생성 후 dto에 get한걸 setter로 만들고
	 * orderRepository를 통해 저장한다.
	 */
	@Transactional
	@PostMapping("/api/orders")
	public ResponseEntity<Object> pushOrder(@RequestBody OrderDto dto, @CookieValue(value="token", required = false) String token) {
		
		if(!jwtService.isValid(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		int memberId = jwtService.getId(token);
		
		Order newOrder = new Order();
		newOrder.setMemberId(memberId);
		newOrder.setName(dto.getName());
		newOrder.setAddress(dto.getAddress());
		newOrder.setPayment(dto.getPayment());
		newOrder.setCardNumber(dto.getCardNumber());
		newOrder.setPlaces(dto.getPlaces());
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());		
		newOrder.setPaymentDate(formatter.format(date));
		
		// 참조변수 orderRepository안에 새로운 결제를 위해 바구니에 저장
		orderRepository.save(newOrder);
		
		// 참조변수 cartRepository안에 담아두었던 장바구니 정보들을
		// 결제창으로 넘어감과 동시에 장바구니를 비워준다(삭제)
		cartRepository.deleteByMemberId(memberId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
