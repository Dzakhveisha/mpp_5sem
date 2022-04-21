package com.example.grapgqldemo.controller.handler;

import com.example.grapgqldemo.controller.config.ErrorMessagesSource;
import com.example.grapgqldemo.service.exception.ArgumentNotValidException;
import com.example.grapgqldemo.service.exception.EntityAlreadyExistException;
import com.example.grapgqldemo.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

    private final ErrorMessagesSource messagesSource;

    @ExceptionHandler(ArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleArgumentNotValidException(ArgumentNotValidException e) {
        return new RestError(messagesSource.getMessage(HttpStatus.BAD_REQUEST.value() + ArgumentNotValidException.CODE,
                e.getCauseMsg()), HttpStatus.BAD_REQUEST, ArgumentNotValidException.CODE);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestError HandleEntityNotFoundException(EntityNotFoundException e) {
        return new RestError(messagesSource.getMessage(HttpStatus.NOT_FOUND.value() + EntityNotFoundException.CODE,
                e.getEntity(), e.getEntityId()), HttpStatus.NOT_FOUND, EntityNotFoundException.CODE);
    }

    @ExceptionHandler({EntityAlreadyExistException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestError HandleEntityAlreadyExist(EntityAlreadyExistException e) {
        return new RestError(messagesSource.getMessage(HttpStatus.BAD_REQUEST.value() + EntityAlreadyExistException.CODE,
                e.getEntityType(), e.getName()), HttpStatus.BAD_REQUEST, EntityAlreadyExistException.CODE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new RestError(messagesSource.getMessage(HttpStatus.BAD_REQUEST.value() + "-00")
                , HttpStatus.BAD_REQUEST, "-00");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new RestError(messagesSource.getMessage(HttpStatus.BAD_REQUEST.value() + "-illegalNumber")
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RestError handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String method = e.getMethod();
        return new RestError(messagesSource.getMessage(HttpStatus.METHOD_NOT_ALLOWED.value() + "-illegalMethod", method),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestError HandleRuntimeExceptions(RuntimeException restException) {
        return new RestError(restException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
