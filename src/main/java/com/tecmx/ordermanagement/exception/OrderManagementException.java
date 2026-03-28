package com.tecmx.ordermanagement.exception;

/**
 * Base exception for the Order Management domain. All custom exceptions in the
 * project must extend this class.
 */
public class OrderManagementException extends RuntimeException {

    private final String errorCode;

    public OrderManagementException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public OrderManagementException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
