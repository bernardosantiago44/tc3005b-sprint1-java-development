package com.tecmx.ordermanagement.exception;

/**
 * Thrown when there is a validation error in the input data.
 */
public class ValidationException extends OrderManagementException {

    private static final String errorCode = "VALIDATION_ERROR";
    private final String fieldName;

    public ValidationException(String message, String fieldName) {
        super(message, errorCode);
        this.fieldName = fieldName;
    }

    public String getFieldName() { return this.fieldName; }
}
