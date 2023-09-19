package com.Super.Board.user.service.advice;


import com.Super.Board.user.dto.MessageResponse;
import com.Super.Board.user.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleBadRequestException(BadRequestException badRequestException) {
        log.warn(badRequestException.toString());
        return new MessageResponse(badRequestException.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse handleNotFoundException(NotFoundException notFoundException) {
        log.warn(notFoundException.toString());
        return new MessageResponse(notFoundException.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResponse handleConlictException(ConflictException conflictException) {
        log.warn(conflictException.toString());
        return new MessageResponse(conflictException.getMessage());
    }

    @ExceptionHandler(JwtIsNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageResponse handleJwtIsNotValidException(JwtIsNotValidException jwtIsNotValidException) {
        log.warn(jwtIsNotValidException.toString());
        return new MessageResponse(jwtIsNotValidException.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageResponse handleDatabaseException(DatabaseException databaseException) {
        log.warn(databaseException.toString());
        return new MessageResponse(databaseException.getMessage());
    }

    @ExceptionHandler(UnathorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageResponse handleDatabaseException(UnathorizedException unathorizedException) {
        log.warn(unathorizedException.toString());
        return new MessageResponse(unathorizedException.getMessage());
    }
}