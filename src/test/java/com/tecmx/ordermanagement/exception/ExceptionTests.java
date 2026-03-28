package com.tecmx.ordermanagement.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the custom exception classes.
 *
 * These tests validate that exceptions are built correctly, contain the
 * expected messages and codes, and maintain the correct hierarchy.
 */
class ExceptionTests {

    @Test
    @DisplayName("OrderManagementException should store message and errorCode")
    void orderManagementExceptionShouldStoreMessageAndCode() {
        OrderManagementException ex = new OrderManagementException("test error", "TEST_CODE");
        assertEquals("test error", ex.getMessage());
        assertEquals("TEST_CODE", ex.getErrorCode());
    }

    @Test
    @DisplayName("OrderManagementException should support chained cause")
    void orderManagementExceptionShouldSupportCause() {
        RuntimeException cause = new RuntimeException("root cause");
        OrderManagementException ex = new OrderManagementException("wrapper", "WRAP", cause);
        assertEquals("wrapper", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    @DisplayName("ResourceNotFoundException should be an instance of OrderManagementException")
    void resourceNotFoundShouldExtendBase() {
        // TODO: After fixing the inheritance, this test should pass.
        //       Create a ResourceNotFoundException and verify with instanceof
        //       that it is an OrderManagementException.
    }

    @Test
    @DisplayName("ResourceNotFoundException should have errorCode RESOURCE_NOT_FOUND")
    void resourceNotFoundShouldHaveCorrectErrorCode() {
        // TODO: Implement - Verify the errorCode.
    }

    @Test
    @DisplayName("ValidationException should be an instance of OrderManagementException")
    void validationExceptionShouldExtendBase() {
        // TODO: Implement
    }

    @Test
    @DisplayName("ValidationException should store the fieldName")
    void validationExceptionShouldStoreFieldName() {
        // TODO: Implement - Create with the constructor that takes fieldName,
        //       verify with getFieldName().
    }

    @Test
    @DisplayName("BusinessRuleException should be an instance of OrderManagementException")
    void businessRuleShouldExtendBase() {
        // TODO: Implement
    }

    @Test
    @DisplayName("BusinessRuleException should support constructor with cause")
    void businessRuleShouldSupportCause() {
        // TODO: Implement
    }
}
