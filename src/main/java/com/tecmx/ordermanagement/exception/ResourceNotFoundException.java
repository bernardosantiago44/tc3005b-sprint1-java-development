package com.tecmx.ordermanagement.exception;

/**
 * Thrown when a resource is not found (order, product, customer).
 */
public class ResourceNotFoundException extends OrderManagementException {

    private final String resourceId;
    private final static String errorCode = "RESOURCE_NOT_FOUND";

    public ResourceNotFoundException(String message, String resourceId) {
        super(message, errorCode);
        this.resourceId = resourceId;
    }

    public String getResourceId() { return this.resourceId; }
}
