package com.tecmx.ordermanagement.exception;

/**
 * Thrown when a business operation cannot be completed due to business rules
 * (e.g. insufficient stock, credit limit exceeded).
 */
public class BusinessRuleException extends OrderManagementException {

    private static final String errorCode = "BUSINESS_RULE_VIOLATION";

    public BusinessRuleException(String message) {
        super(message, errorCode);
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(message, errorCode, cause);
    }
}
