package wooteco.subway.web.member.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wooteco.subway.infra.JwtTokenProvider;
import wooteco.subway.web.member.AuthorizationExtractor;
import wooteco.subway.web.member.InvalidAuthenticationException;
import wooteco.subway.web.member.exception.NotMatchedEmailIExistInJwtException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {
    private AuthorizationExtractor authExtractor;
    private JwtTokenProvider jwtTokenProvider;

    public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtTokenProvider jwtTokenProvider) {
        this.authExtractor = authExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        if (isPost(request)) {
            return true;
        }

        String bearer = authExtractor.extract(request, "Bearer");
        System.out.println(bearer + "베어러 입니다");
        validateToken(bearer);
        String email = jwtTokenProvider.getSubject(bearer);
        request.setAttribute("loginMemberEmail", email);
        if(isGet(request)) {
            validateEmailEquals(request, email);
        }
        return true;
    }

    private void validateEmailEquals(HttpServletRequest request, String email) {
        if(request.getParameter("email").equals(email) == false) {
            System.out.println(request.getParameter("email") + "여기라구요123123" + email);
            throw new NotMatchedEmailIExistInJwtException(email);
        }
    }

    private boolean isGet(HttpServletRequest request) {
        return GET.matches(request.getMethod());
    }

    private boolean isPost(HttpServletRequest request) {
        return POST.matches(request.getMethod());
    }

    private void validateToken(String bearer) {
        if (bearer.isEmpty() || !jwtTokenProvider.validateToken(bearer)) {
            throw new InvalidAuthenticationException();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
