package com.vue3type_travel.web.dao;

import java.util.List;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vue3type_travel.web.entity.Cart;

@Repository
@Mapper
public class CartDaoImpl implements CartDao {
	
	@Autowired
	SqlSession sqlSession;
	
	//protected Log log = LogFactory.getLog(this.getClass());	

	@Override
	public List<Cart> getCartList(int param) {
		//log.debug("getCartList ==================>" + param );
		System.out.println("여기 memberId123 : " + param);
		return sqlSession.selectList("com.vue3type_travel.web.dao.CartDao.getCartList", param);
	}
}
