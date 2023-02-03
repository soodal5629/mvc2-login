package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        // @Login 어노테이션이 파라미터에 있는지 확인(@Login Member member)
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        // Member 클래스 타입인지 확인
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        // true 일 경우 밑의 메소드 (resolveArgument) 실행
        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    // 컨트롤러 호출 직전에 호출되어 필요한 파라미터 정보 생성해줌(@ModelAttribute, @RequestParam 등 처럼
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if(session == null) { // 세션이 null이면 @Login Member member <= Member에 null 넣음
            return null;
        }
        // 없으면 member에 null 들어가서 리턴됨
        // 있으면 member 반환
        Object member = session.getAttribute(SessionConst.LOGIN_MEMBER);
        return member;
    }
}
