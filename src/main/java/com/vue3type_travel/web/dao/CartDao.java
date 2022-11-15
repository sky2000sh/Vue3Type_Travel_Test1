package com.vue3type_travel.web.dao;

import java.util.List;
import com.vue3type_travel.web.entity.Cart;

public interface CartDao {
	
	public List<Cart> getCartList(int param);
	
	public List<Cart> getSelectedCartList(List<Integer> param);

}
