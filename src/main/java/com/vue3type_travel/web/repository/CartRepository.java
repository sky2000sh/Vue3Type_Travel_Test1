package com.vue3type_travel.web.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vue3type_travel.web.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	
	List<Cart> findByMemberId(int memberId);
	
	// 사용자의 id값과 item id가 있으면, 카트 정보를 가져온다.
	Cart findByMemberIdAndPlaceId(int memberId, int placeId);
	
	void deleteByMemberId(int memberId);
}
