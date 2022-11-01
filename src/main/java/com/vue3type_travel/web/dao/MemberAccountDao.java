package com.vue3type_travel.web.dao;

import com.vue3type_travel.web.entity.Member;

public interface MemberAccountDao {
	
	public int memberSignUp(Member vo);
	
	public String getMemberEmailFromPwd(String string);

}
