package com.huijiewei.agile.boot.admin.api.handler;

import com.huijiewei.agile.base.exception.BadRequestException;
import com.huijiewei.agile.base.exception.ForbiddenException;
import com.huijiewei.agile.base.exception.NotFoundException;
import org.apiguardian.api.API;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.autoconfigure.security.SpringSecurityExceptionHandling;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.zalando.problem.Status.UNPROCESSABLE_ENTITY;

@ControllerAdvice
@Order(-99)
public class ExceptionHandling extends SpringSecurityExceptionHandling {
    @Override
    public StatusType defaultConstraintViolationStatus() {
        return UNPROCESSABLE_ENTITY;
    }

    @Override
    public boolean isCausalChainsEnabled() {
        return false;
    }

    @API(status = INTERNAL)
    @ExceptionHandler
    public ResponseEntity<Problem> handleNotFound(
            final NotFoundException exception,
            final NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @API(status = INTERNAL)
    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequest(
            final BadRequestException exception,
            final NativeWebRequest request) {
        return create(Status.BAD_REQUEST, exception, request);
    }

    @API(status = INTERNAL)
    @ExceptionHandler
    public ResponseEntity<Problem> handleForbidden(
            final ForbiddenException exception,
            final NativeWebRequest request) {
        return create(Status.FORBIDDEN, exception, request);
    }
}
