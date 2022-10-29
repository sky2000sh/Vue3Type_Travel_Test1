package com.vue3type_travel.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vue3type_travel.web.entity.Place;
import com.vue3type_travel.web.repository.PlaceRepository;

@RestController
public class PlaceController {
	
	@Autowired
	PlaceRepository placeRepository;
	
	@GetMapping("/api/places")
	public List<Place> getPlace() {
		
//		List<String> places = new ArrayList<>();
//		places.add("premier");
//		places.add("seconde");
//		places.add("troisieme");
		
		List<Place> place = placeRepository.findAll();
		
		return place;		
	}

}
