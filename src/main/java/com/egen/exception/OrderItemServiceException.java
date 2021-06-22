package com.egen.exception;

public class OrderItemServiceException extends RuntimeException {
    public OrderItemServiceException(String message) {
        super(message);
    }
}
