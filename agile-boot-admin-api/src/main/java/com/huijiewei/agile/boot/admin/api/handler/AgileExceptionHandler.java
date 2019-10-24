package com.huijiewei.agile.boot.admin.api.handler;

import com.huijiewei.agile.base.exception.BadRequestException;
import com.huijiewei.agile.base.exception.NotFoundException;
import org.apiguardian.api.API;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.zalando.problem.Status.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public interface AgileExceptionHandler extends ProblemHandling {
    default StatusType defaultConstraintViolationStatus() {
        return UNPROCESSABLE_ENTITY;
    }

    default boolean isCausalChainsEnabled() {
        return true;
    }

    @API(status = INTERNAL)
    @ExceptionHandler
    default ResponseEntity<Problem> handleNotFound(
            final NotFoundException exception,
            final NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @API(status = INTERNAL)
    @ExceptionHandler
    default ResponseEntity<Problem> handleBadRequest(
            final BadRequestException exception,
            final NativeWebRequest request) {
        return create(Status.BAD_REQUEST, exception, request);
    }
}
