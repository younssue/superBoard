package com.Super.Board.user.exception;

public class JwtIsNotValidException extends RuntimeException {
    public JwtIsNotValidException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
