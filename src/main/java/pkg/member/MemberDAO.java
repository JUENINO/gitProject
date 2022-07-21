package pkg.member;

import java.util.List;
import java.util.Map;

public interface MemberDAO {
    public String loginchk(Map<String,Object> map); // 로그인체크
    public String chk(Map<String, Object> map); // 아이디, 닉네임 중복체크
	public String insmem(Map<String, Object> map); // 회원가입
	public List<MemberVO> selmem(Map<String, Object> map); // 멤버정보(일반페이지)
	public String updatemem(Map<String, Object> map); // 멤버정보수정(일반회원)
	public Map<String, Object>  showmemberList(Map<String, Object> map); // 멤버정보(관리자페이지)
	public Map<String, Object> updatememberList(Map<String, Object> map);// 멤버정보수정(관리자페이지)
	
}
