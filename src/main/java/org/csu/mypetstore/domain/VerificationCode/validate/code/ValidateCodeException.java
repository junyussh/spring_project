package org.csu.mypetstore.domain.VerificationCode.validate.code;


import org.springframework.security.core.AuthenticationException;


public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 2672899097153524723L;

    public ValidateCodeException(String explanation) {
        super(explanation);
    }
}
