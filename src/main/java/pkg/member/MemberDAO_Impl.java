package pkg.member;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class MemberDAO_Impl implements MemberDAO{

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Override	
	public String loginchk(Map<String, Object> map) { // 로그인 체크
		sqlSessionTemplate.selectList("loginchk",map);
		if(map.get("error1")==null) {
			return (String) map.get("result");	
		}
		
		return "FAIL";
	}

	public String chk(Map<String, Object> map) { // ID,닉네임 중복체크
		Map<String, Object> hmep = (Map<String,Object>)map.get("data");
		System.out.println(hmep);
		sqlSessionTemplate.selectList("chk",hmep);	
		
		return (String) hmep.get("result");
	}
	@Override
	public String insmem(Map<String, Object> map) { // 회원가입
		System.out.println(map);
		sqlSessionTemplate.selectList("insmem",map);
		System.out.println(map.get("error"));	
		System.out.println(map.get("error2"));	
		
		
		return (String)map.get("result");
	}
	@Override
	public List<MemberVO> selmem(Map<String, Object> map) { // 회원 정보(일반페이지)
		sqlSessionTemplate.selectList("selmem",map);

		
		return (List<MemberVO>)map.get("result");
	}
	@Override
	public String updatemem(Map<String, Object> map) { // 회원정보수정 (본인)
		sqlSessionTemplate.selectList("updatemem",map);
		System.out.println((String)map.get("result"));
		System.out.println((String)map.get("error"));
		System.out.println((String)map.get("error2"));
		
		return (String)map.get("result");
	}

	@Override
	public Map<String, Object>  showmemberList(Map<String, Object> map) { // 회원 멤버리스트 보여주기(관리자페이지)
		System.out.println("showmemberlist");
		sqlSessionTemplate.selectList("memberlist",map);
		System.out.println(map.get("result"));
		System.out.println(map.get("maxpage"));
		System.out.println(map.get("errormsg"));
		
		return map;
	}

	@Override
	public Map<String, Object> updatememberList(Map<String, Object> map) { // 회원 멤버리스트 수정(관리자페이지)
		
		
		List<Map<String, Object>> members = (List<Map<String, Object>>)map.get("data");
		System.out.println("update dao 발동");
		for(Map<String, Object> member : members) {
			sqlSessionTemplate.selectList("updatememlist",member);
			System.out.println("업데이트발동");
		}
		
		return null;
	}

}
