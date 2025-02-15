package com.javaacademy.Cinema.exception;

public class TicketIsPaidException extends RuntimeException {
    public TicketIsPaidException(String message) {
        super(message);
    }
}
