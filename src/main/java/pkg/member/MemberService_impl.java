package pkg.member;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService_impl implements MemberService {

	@Autowired
	MemberDAO memberdao;
	
	@Override
	public String loginchk(Map<String, Object> map) { // 로그인 체크 (id,비밀번호)
		
		return memberdao.loginchk(map);
	}
	@Override
	public String chk(Map<String, Object> map) { // 닉네임, id 중복체크 (id,nicknmae)
		return memberdao.chk(map);
	}
	@Override
	public String insmem(Map<String, Object> map) { // 회원가입 
		return memberdao.insmem(map);
	}
	@Override
	public List<MemberVO> selmem(Map<String, Object> map) { // 내정보 보기
		
		return memberdao.selmem(map);
	}
	@Override
	public String updatemem(Map<String, Object> map) { // 내정보 수정
		
		return memberdao.updatemem(map);
	}
	@Override
	public Map<String, Object> showmemberList(Map<String, Object> map) { // 관리자 페이지 멤버 리스트
		// TODO Auto-generated method stub
		return memberdao.showmemberList(map);
	}
	@Override
	public Map<String, Object> updatememberList(Map<String, Object> map) { // 관리자 페이지 멤버 수정
		// TODO Auto-generated method stub
		return memberdao.updatememberList(map);
	}
    
}
