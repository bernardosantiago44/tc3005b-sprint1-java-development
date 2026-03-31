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
        Product product = new Product("001", "test product", 100.0, 3);
        OrderItem item = new OrderItem(product, 3);

        var subtotal = item.getSubtotal();
        assertEquals(300.0, subtotal);
    }

    @Test
    @DisplayName("OrderItem.getSubtotal() should throw ValidationException if quantity <= 0")
    void orderItemSubtotalShouldThrowWhenQuantityInvalid() {
        Product product = new Product("01", "test product", 100.0, 3);
        OrderItem zeroItem = new OrderItem(product, 0);
        OrderItem negativeItem = new OrderItem(product, -1);

        assertThrows(ValidationException.class, zeroItem::getSubtotal);
        assertThrows(ValidationException.class, negativeItem::getSubtotal);
    }

    // =========================================================================
    // Order.getTotal() tests
    // =========================================================================
    @Test
    @DisplayName("Order.getTotal() should calculate the sum of all subtotals")
    void orderTotalShouldSumAllSubtotals() {
        Product product1 = new Product("01", "test product 1", 50, 2);
        Product product2 = new Product("02", "test product 2", 30, 3);

        OrderItem item1 = new OrderItem(product1, 2);
        OrderItem item2 = new OrderItem(product2, 3);

        Order order = new Order("or1", "01");
        order.addItem(item1);
        order.addItem(item2);

        assertEquals(100.0, item1.getSubtotal());
        assertEquals(90.0, item2.getSubtotal());
        assertEquals(190, order.getTotal());
    }

    @Test
    @DisplayName("Order.getTotal() should throw BusinessRuleException if it has no items")
    void orderTotalShouldThrowWhenNoItems() {
        Order order = new Order("or1", "01");
        
        assertThrows(BusinessRuleException.class, order::getTotal);
    }

    // =========================================================================
    // Constructor and getter/setter tests
    // =========================================================================
    @Test
    @DisplayName("Order should be created with CREATED status by default")
    void orderShouldHaveCreatedStatusByDefault() {
        Order order = new Order("or1", "01");
        
        assertEquals(Order.Status.CREATED, order.getStatus());
    }

    @Test
    @DisplayName("Product should correctly store all its fields")
    void productShouldStoreAllFields() {
        Product product = new Product("prod-01", "test product", 100, 5);
        
        assertEquals("prod-01", product.getId());
        assertEquals("test product", product.getName());
        assertEquals(100, product.getPrice());
        assertEquals(5, product.getStockQuantity());
        assertEquals(Product.class, product.getClass());
    }
}
