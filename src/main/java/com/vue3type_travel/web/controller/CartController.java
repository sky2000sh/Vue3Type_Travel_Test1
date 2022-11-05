package com.vue3type_travel.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vue3type_travel.web.entity.Cart;
import com.vue3type_travel.web.entity.Place;
import com.vue3type_travel.web.repository.CartRepository;
import com.vue3type_travel.web.repository.PlaceRepository;
import com.vue3type_travel.web.service.JwtService;

@RestController
public class CartController {

	@Autowired
	JwtService jwtService;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	PlaceRepository placeRepository;
	
	// cart 정보를 가져오기
	@GetMapping("/api/cart/places")
	public ResponseEntity<List<Place>> getCartItems(@CookieValue(value="token", required = false) String token) {
		
		if(!jwtService.isValid(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		int memberId = jwtService.getId(token);
		List<Cart> carts = cartRepository.findByMemberId(memberId);
		
		// youtube보고 따라한 아래 명령어를 먹히지 않았음 => 그래서 collect()함수를 찾아 대체했음.
		// List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList();
		// 아래 카트의 정보를 가져오면 vo 한 개에 대한 정보만 가져올테니, list로 가져와서 다건을 처리할 수 있도록 해준다.
		List<Integer> placeIds = carts.stream().map(Cart::getPlaceId).collect(Collectors.toList());
		
		// 다건으로 처리된 list형식을 items 참조변수에 담는다.
		List<Place> places =placeRepository.findByIdIn(placeIds);
		
		return new ResponseEntity<>(places, HttpStatus.OK);
	}
	
	// cart의 정보를 넣기(push)
	@PostMapping("/api/cart/places/{placeId}")
	public ResponseEntity<Object> pushCartItem(@PathVariable("placeId") int placeId, @CookieValue(value="token", required = false) String token) {
		
		if(!jwtService.isValid(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		int memberId = jwtService.getId(token);		
		Cart cart = cartRepository.findByMemberIdAndPlaceId(memberId, placeId);
		
		// cart가 없을 때는 새로운 cart를 추가하는데, 아래 명령어로 memberId, itemId를 인자로 받아서 set으로 세팅해준다.
		if(cart == null) {
			Cart newCart = new Cart();
			newCart.setMemberId(memberId);
			newCart.setPlaceId(placeId);
			cartRepository.save(newCart);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// cart의 정보 목록을 삭제하기(remove)
	@DeleteMapping("/api/cart/places/{placeId}")
	public ResponseEntity<Object> removeCartItem(@PathVariable("placeId") int placeId, @CookieValue(value="token", required = false) String token) {
		
		if(!jwtService.isValid(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		int memberId = jwtService.getId(token);
		Cart cart = cartRepository.findByMemberIdAndPlaceId(memberId, placeId);
		
		cartRepository.delete(cart);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
