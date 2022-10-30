package com.vue3type_travel.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.vue3type_travel.web.entity.Member;

@Repository
public class MemberAccountDaoImpl implements MemberAccountDao {

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public int memberSignUp(Member vo) {
		//MemberAccountDao dao = sqlSession.getMapper(MemberAccountDao.class);
		//dao.memberSignUp(vo);
		return sqlSession.insert("com.vue3type_travel.web.dao.MemberAccountDao.memberSignUp", vo);
	}

}
