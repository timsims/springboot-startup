package com.bootstrap.startup.exceptions;

import com.bootstrap.startup.http.response.BasicResponse;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author timsims
 */
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BasicResponse handle(MethodArgumentNotValidException exception) {
        var r = new BasicResponse<FieldError>();
        r.setStatus(422);
        r.setData(exception.getBindingResult().getFieldError());
        r.setMessage("校验错误");
        return r;
    }
}
