package io.github.talelin.autoconfigure.exception;

import io.github.talelin.autoconfigure.bean.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * 认证异常
 *
 * @author pedro@TaleLin
 */
public class AuthorizationException extends HttpException {

    @Getter
    protected int httpCode = HttpStatus.UNAUTHORIZED.value();

    @Getter
    protected int code = Code.UN_AUTHORIZATION.getCode();

    public AuthorizationException() {
        super(Code.UN_AUTHORIZATION.getDescription(), Code.UN_AUTHORIZATION.getCode());
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(int code) {
        super(Code.UN_AUTHORIZATION.getDescription(), code);
        this.code = code;
    }

    public AuthorizationException(String message, int code) {
        super(message, code);
        this.code = code;
    }
}
