package com.vue3type_travel.web.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vue3type_travel.web.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
	
	List<Place> findByIdIn(List<Integer> ids);

}
