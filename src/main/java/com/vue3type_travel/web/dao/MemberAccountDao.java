package com.vue3type_travel.web.dao;

import com.vue3type_travel.web.entity.Member;
import com.vue3type_travel.web.entity.MemberInfo;

public interface MemberAccountDao {
	
	public int memberSignUp(Member vo);
	
	public int memberInfoSignUp(MemberInfo infoVo);
	
	public String getMemberEmailFromPwd(String string);

}
