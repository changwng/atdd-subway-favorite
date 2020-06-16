package wooteco.subway.web.member.exception;

import javax.security.sasl.AuthenticationException;

public class InvalidAuthenticationException extends AuthenticationException {
    private static final String MESSAGE = "잘못된 토큰 값 입니다.";

    public InvalidAuthenticationException(String message) {
        super(message);
    }

    public InvalidAuthenticationException() {
        super(MESSAGE);
    }
}