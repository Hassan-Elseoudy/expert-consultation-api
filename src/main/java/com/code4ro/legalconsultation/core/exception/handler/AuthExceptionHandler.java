package com.code4ro.legalconsultation.core.exception.handler;

import com.code4ro.legalconsultation.authentication.controller.AuthController;
import com.code4ro.legalconsultation.core.exception.ExceptionResponse;
import com.code4ro.legalconsultation.i18n.model.I18nError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(final BadCredentialsException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setI18nErrors(Collections.singletonList(
                new I18nError("login.Bad.credentials")));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}
