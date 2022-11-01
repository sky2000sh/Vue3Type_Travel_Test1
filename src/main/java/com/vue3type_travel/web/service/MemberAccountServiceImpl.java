package com.vue3type_travel.web.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.vue3type_travel.web.dao.MemberAccountDaoImpl;
import com.vue3type_travel.web.entity.Member;

@Service
public class MemberAccountServiceImpl implements MemberAccountService {

	@Autowired
	MemberAccountDaoImpl dao;

	@Override
	public void memberSignUp(Member vo) {
		dao.memberSignUp(vo);		
	}

}
