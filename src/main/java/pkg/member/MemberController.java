package pkg.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

   
    @RequestMapping("login") // 로그인  -> 로그인페이지
    public String login (Model model, HttpServletRequest request, HttpServletResponse response) {
    	return "login";
    }
    @RequestMapping("logout")
    public String logout (HttpServletRequest request) { // 로그아웃
    	System.out.println("call logout");
    	HttpSession session = request.getSession();
    	session.invalidate();
        return "redirect:"+(String)request.getHeader("Referer");
    };
    @RequestMapping("register")
    public String register () { // 회원가입

        return "register";
    }
    @RequestMapping("profile") //profile
    public String profile () {

        return "userProfile";
    }
    
    public String resign () {
        
        return "";
    }

}
