package com.vue3type_travel.web.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vue3type_travel.web.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByMemberIdOrderByIdDesc(int memberId);

}
