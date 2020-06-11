package wooteco.subway.web.member.interceptor;

import java.lang.annotation.Annotation;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import wooteco.subway.infra.JwtTokenProvider;
import wooteco.subway.web.member.AuthorizationExtractor;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {
    private static final String EMAIL_ATTRIBUTE = "loginMemberEmail";

    private AuthorizationExtractor authExtractor;
    private JwtTokenProvider jwtTokenProvider;

    public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtTokenProvider jwtTokenProvider) {
        this.authExtractor = authExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) {
        IsAuth annotation = getAnnotation((HandlerMethod)handler, IsAuth.class);
        Auth auth;
        if (!ObjectUtils.isEmpty(annotation)) {
            auth = annotation.isAuth();
            if (auth == Auth.AUTH) {
                String bearer = authExtractor.extract(request);
                jwtTokenProvider.validateToken(bearer);
                String email = jwtTokenProvider.getSubject(bearer);
                request.setAttribute(EMAIL_ATTRIBUTE, email);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) throws Exception {

    }

    private <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationType) {
        return Optional.ofNullable(handlerMethod.getMethodAnnotation(annotationType))
            .orElse(handlerMethod.getBeanType().getAnnotation(annotationType));
    }
}
