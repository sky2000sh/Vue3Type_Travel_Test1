package com.vue3type_travel.web.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	
	/* logback.xml 파일 :
	 * 	root 태그는 level이라는 속성을 가지고있다.
	 * 	해당속성은 log를 찍을 레벨을 설정.
	 * 	level은 총 5가지 단계로 구별되며 설정된 레벨 이상의 로그만 출력.
	 * 	TRACE - DEBUG - INFO -WARN - ERROR */
	protected Log log = LogFactory.getLog(this.getClass());

	// 회원의 카트 목록 조회
	@Override
	public List<Cart> getCartList(int param) {
		log.info("getCartList ==================> " + param );
		return sqlSession.selectList("com.vue3type_travel.web.dao.CartDao.getCartList", param);
	}
}
