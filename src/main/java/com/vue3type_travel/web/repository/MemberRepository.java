package com.vue3type_travel.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vue3type_travel.web.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	// Member findByEmailAndPassword(String email, String password);
	
	Member findByEmailAndPassword(String email, String password);

}
