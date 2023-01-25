package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //@GetMapping("/")
    public String home() {
        return "home";
    }

    //@GetMapping("/")
    // 로그인 됐을 때도 처리해주는 메소드 - 로그인 여부에 따라 보여주는 화면이 다름
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if(memberId == null) {
            return "home";
        }
        // 로그인한 사용자
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null) { // DB 에서 찾은 멤버가 없을 경우
            return "home";
        }
        model.addAttribute("member", loginMember);

        return "loginHome";
    }

    //@GetMapping("/")
    // 로그인 됐을 때도 처리해주는 메소드 - 로그인 여부에 따라 보여주는 화면이 다름
    public String homeLoginV2(HttpServletRequest request, Model model) {
        // 세션 관리자에 저장된 회원정보 조회
        Member member = (Member) sessionManager.getSession(request);

        // 로그인한 사용자

        if(member == null) { // DB 에서 찾은 멤버가 없을 경우
            return "home";
        }
        model.addAttribute("member", member);

        return "loginHome";
    }

    //@GetMapping("/")
    // 로그인 됐을 때도 처리해주는 메소드 - 로그인 여부에 따라 보여주는 화면이 다름
    public String homeLoginV3(HttpServletRequest request, Model model) {
        // 화면에 처음 진입한 사람들한테도 세션이 생성되면 안되기 때문에 false
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 home
        if(loginMember == null) { // DB 에서 찾은 멤버가 없을 경우
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    // 로그인 됐을 때도 처리해주는 메소드 - 로그인 여부에 따라 보여주는 화면이 다름
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            , Model model) {
        // 세션에 회원 데이터가 없으면 home
        if(loginMember == null) { // DB 에서 찾은 멤버가 없을 경우
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}