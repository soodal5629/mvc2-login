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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/")
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

}