package pkg.member;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class MemberController_JSON {

	@Autowired
	MemberService memberService;

	// 로그인
	@PostMapping("loginchk")
	public String loginchk(@RequestBody Map<String, Object> map, HttpSession session) {
		System.out.println(memberService.loginchk(map));
		if(!(memberService.loginchk(map).equals("FAIL"))) {
			session.setAttribute("memberid", memberService.loginchk(map));
			JsonObject obj = new JsonObject();	
			obj.addProperty("result", "SUC");
			List<MemberVO> list = memberService.selmem(map);
			
			// TODO : 임시로 세션에 값 달아주었음, 나머지도 추가해야함
			session.setAttribute("memberProfile", list.get(0).getMemberprofile());
			return obj.toString();
		}
		
		return "{\"result\": \"FAIL\" }";
		
	}
	// 아이디 중복 체크용
	@PostMapping("chk")
	public String chk(@RequestBody Map<String,Object> map) {
		
		JsonObject obj = new JsonObject();
		obj.addProperty("result", memberService.chk(map));
		
		return  obj.toString();
	}
	//회원가입
	@PostMapping("register")
	public String gaip(@RequestBody Map<String,Object> map) {
		Map<String, Object> jmap = (Map<String, Object>) map.get("data");
		String memberid = (String)jmap.get("memberid");
	
		if (memberid.equals(memberService.insmem(jmap))) {
			
			return "{\"result\": \"SUC\" }";
		}
		
		
		
		return "{\"result\": \"FAIL\" }";
		
	}


	// 회원정보수정(회원용)

	@PostMapping("myform") // profile // profile?memberId=this9999
	public String myform(@RequestBody Map<String,Object> map) {
		List<MemberVO> memlist = (List<MemberVO>)memberService.selmem(map);
		System.out.println("----myform-----");
		System.out.println(map);
		JsonObject obj = new JsonObject();
		for(MemberVO vo : memlist) {
		
			obj.addProperty("memberid", vo.getMemberid());
			obj.addProperty("memberbirth", vo.getMemberbirth());
			obj.addProperty("memberauth", vo.getMemberauth());
			obj.addProperty("membergender", vo.getMembergender());
			obj.addProperty("memberhob1", vo.getMemberhob1());
			obj.addProperty("memberhob2", vo.getMemberhob2());
			obj.addProperty("memberhob3", vo.getMemberhob3());
			obj.addProperty("membernickname", vo.getMembernickname());
			obj.addProperty("memberprofile", vo.getMemberprofile());
			obj.addProperty("membersigndate", vo.getMembersigndate());
			
			System.out.println(vo.getMemberid());
			System.out.println(vo.getMemberbirth());
			System.out.println(vo.getMemberauth());
			System.out.println(vo.getMembergender());
			System.out.println(vo.getMemberhob1());
			System.out.println(vo.getMemberhob2());
			System.out.println(vo.getMemberhob3());
			System.out.println(vo.getMembernickname());
			System.out.println(vo.getMemberprofile());
			System.out.println(vo.getMembersigndate());			
		}
		System.out.println("-------");
		return obj.toString();
	}

	@PostMapping("updatemember")
	public String updateprofile(@RequestBody Map<String,Object> map) {
		System.out.println("----update controller");
		System.out.println(map.get("updatedata"));
		memberService.selmem(map);
		
		if(memberService.updatemem((Map<String, Object>) map.get("updatedata")).equals("SUC")) {
			System.out.println("성공");
			return "{\"result\": \"SUC\" }";
		}
		
		System.out.println("실패");
		return "{\"result\": \"FAIL\" }";
	}
	//관리자 페이지 멤버리스트 
	@PostMapping("memberform")
	public String memberform(@RequestBody Map<String, Object> map) {
			System.out.println("pageNum : " +map.get("pagenum"));
			Map<String, Object> memberform = memberService.showmemberList(map);
			List<MemberVO> allmemlist = (List<MemberVO>) memberform.get("result");
			JsonObject resultobj = new JsonObject();
			JsonArray arr = new JsonArray();			
			for(MemberVO vo : allmemlist) {
				JsonObject obj = new JsonObject();
				obj.addProperty("memberidx", vo.getMemberidx());
				obj.addProperty("memberid", vo.getMemberid());
				obj.addProperty("memberbirth", vo.getMemberbirth());
				obj.addProperty("memberauth", vo.getMemberauth());
				obj.addProperty("membergender", vo.getMembergender());
				obj.addProperty("memberhob1", vo.getMemberhob1());
				obj.addProperty("memberhob2", vo.getMemberhob2());
				obj.addProperty("memberhob3", vo.getMemberhob3());
				obj.addProperty("membernickname", vo.getMembernickname());
				obj.addProperty("memberprofile", vo.getMemberprofile());
				obj.addProperty("membersigndate", vo.getMembersigndate());
				arr.add(obj);
				System.out.println("----");
				System.out.println(vo.getMemberidx());
				System.out.println(vo.getMemberid());
				System.out.println(vo.getMemberbirth());
				System.out.println(vo.getMemberauth());
				System.out.println(vo.getMembergender());
				System.out.println(vo.getMemberhob1());
				System.out.println(vo.getMemberhob2());
				System.out.println(vo.getMemberhob3());
				System.out.println(vo.getMembernickname());
				System.out.println(vo.getMemberprofile());
				System.out.println(vo.getMembersigndate());	
				System.out.println("----");
			}
			System.out.println("------jsoncontroller");
			resultobj.add("result", arr);
			resultobj.addProperty("maxpage", (String) map.get("maxpage"));
			System.out.println(map.get("maxpage"));
			
			return resultobj.toString();
			//return new Gson().toJson(allmemlist);
	}
	
	//멤버 정보보기(다른 고객, 고객 본인)
	@PostMapping("profile") 
	public String profile(@RequestBody Map<String,Object> map, HttpSession session) {
		System.out.println(map.get("memberid"));
		
		boolean isOwn = false;
		
		if (session==null) {
			isOwn = false;
		}		
		else if(session.getAttribute("memberid").equals(map.get("memberid"))) {
			isOwn = true;
		}
		else {
			isOwn = false;
		}
		
		List<MemberVO> memlist = (List<MemberVO>)memberService.selmem(map);
		JsonObject obj = new JsonObject();
		for(MemberVO vo : memlist) {
			
			obj.addProperty("memberid", vo.getMemberid());
			obj.addProperty("memberbirth", vo.getMemberbirth());
			obj.addProperty("memberauth", vo.getMemberauth());
			obj.addProperty("membergender", vo.getMembergender());
			obj.addProperty("memberhob1", vo.getMemberhob1());
			obj.addProperty("memberhob2", vo.getMemberhob2());
			obj.addProperty("memberhob3", vo.getMemberhob3());
			obj.addProperty("membernickname", vo.getMembernickname());
			obj.addProperty("memberprofile", vo.getMemberprofile());
			obj.addProperty("membersigndate", vo.getMembersigndate());
			obj.addProperty("isOwn", isOwn);
			System.out.println(vo.getMemberid());
			System.out.println(vo.getMemberbirth());
			System.out.println(vo.getMemberauth());
			System.out.println(vo.getMembergender());
			System.out.println(vo.getMemberhob1());
			System.out.println(vo.getMemberhob2());
			System.out.println(vo.getMemberhob3());
			System.out.println(vo.getMembernickname());
			System.out.println(vo.getMemberprofile());
			System.out.println(vo.getMembersigndate());			
		
		}
		
				
	
		return obj.toString();
		
		
	}
	//회원정보 수정 (관리자페이지)
	@PostMapping("memberlistupdate")
	public String memberlistUpdate(@RequestBody Map<String,Object> map) {
		memberService.updatememberList(map);
		
		return "";
	}
	
	
	
}
