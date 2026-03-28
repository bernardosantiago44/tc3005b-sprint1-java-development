package com.tecmx.ordermanagement.model;

import com.tecmx.ordermanagement.exception.BusinessRuleException;
import com.tecmx.ordermanagement.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the model classes (Order, OrderItem).
 *
 * These tests do NOT require Mockito — they are pure unit tests.
 */
class ModelTests {

    // =========================================================================
    // OrderItem.getSubtotal() tests
    // =========================================================================
    @Test
    @DisplayName("OrderItem.getSubtotal() should correctly calculate the subtotal")
    void orderItemSubtotalShouldBeCorrect() {
        // TODO: Arrange - Create a Product with price=100.0 and an OrderItem with quantity=3.
        // TODO: Act - Call getSubtotal().
        // TODO: Assert - The result should be 300.0.
    }

    @Test
    @DisplayName("OrderItem.getSubtotal() should throw ValidationException if quantity <= 0")
    void orderItemSubtotalShouldThrowWhenQuantityInvalid() {
        // TODO: Implement
    }

    // =========================================================================
    // Order.getTotal() tests
    // =========================================================================
    @Test
    @DisplayName("Order.getTotal() should calculate the sum of all subtotals")
    void orderTotalShouldSumAllSubtotals() {
        // TODO: Arrange - Create an order with 2 items:
        //       Item 1: Product(price=50.0), quantity=2 → subtotal=100.0
        //       Item 2: Product(price=30.0), quantity=3 → subtotal=90.0
        // TODO: Act & Assert - getTotal() should return 190.0
    }

    @Test
    @DisplayName("Order.getTotal() should throw BusinessRuleException if it has no items")
    void orderTotalShouldThrowWhenNoItems() {
        // TODO: Implement
    }

    // =========================================================================
    // Constructor and getter/setter tests
    // =========================================================================
    @Test
    @DisplayName("Order should be created with CREATED status by default")
    void orderShouldHaveCreatedStatusByDefault() {
        // TODO: Implement
    }

    @Test
    @DisplayName("Product should correctly store all its fields")
    void productShouldStoreAllFields() {
        // TODO: Implement - Create a Product with the full constructor
        //       and verify each getter.
    }
}
