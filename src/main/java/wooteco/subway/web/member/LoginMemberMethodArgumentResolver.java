package wooteco.subway.web.member;

import static org.springframework.web.context.request.RequestAttributes.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String LOGIN_MEMBER_ID = "loginMemberId";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMemberId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String id = (String)webRequest.getAttribute(LOGIN_MEMBER_ID, SCOPE_REQUEST);
        if (StringUtils.isBlank(id)) {
            throw new InvalidAuthenticationException("유효하지 않은 토큰입니다!!");
        }
        return Long.valueOf(id);
    }
}
