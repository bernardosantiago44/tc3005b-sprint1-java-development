package com.tecmx.ordermanagement.exception;

/**
 * Thrown when a business operation cannot be completed due to business rules
 * (e.g. insufficient stock, credit limit exceeded).
 *
 * TODO: Make this class extend OrderManagementException. Use the errorCode
 * "BUSINESS_RULE_VIOLATION".
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
        // TODO: Call the OrderManagementException constructor with the correct errorCode.
    }

    // TODO: Add a constructor that accepts a Throwable cause for exception chaining.
}
