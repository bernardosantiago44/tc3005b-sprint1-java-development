package com.tecmx.ordermanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecmx.ordermanagement.model.Order;
import com.tecmx.ordermanagement.repository.OrderRepository;

/**
 * Main order management service.
 *
 * Students must complete the implementation following the instructions in each
 * TODO. Each method must: 1. Validate inputs and throw the appropriate
 * exception. 2. Log important operations at the correct level. 3. Be testable
 * with Mockito.
 */
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new order for a customer.
     *
     * TODO: 1. Validate that orderId is not null or empty → throw
     * ValidationException. 2. Validate that customerId is not null or empty →
     * throw ValidationException. 3. Verify that no order with the same ID
     * exists → throw BusinessRuleException. 4. Create the order, save it in the
     * repository. 5. Log at INFO level: "Order created: {orderId} for customer:
     * {customerId}". 6. Return the created order.
     */
    public Order createOrder(String orderId, String customerId) {
        // TODO: Implement
        return null;
    }

    /**
     * Adds a product to an existing order.
     *
     * TODO: 1. Find the order by ID → if not found, throw
     * ResourceNotFoundException. 2. Find the product by ID → if not found,
     * throw ResourceNotFoundException. 3. Validate that quantity > 0 → throw
     * ValidationException if not. 4. Validate that there is sufficient stock →
     * throw BusinessRuleException if not. 5. Reduce the product stock by the
     * requested quantity. 6. Create an OrderItem and add it to the order. 7.
     * Save the updated order and product. 8. Log at INFO level: "Added
     * {quantity}x {productName} to order {orderId}". 9. Log at DEBUG level:
     * "Remaining stock for {productId}: {newStock}".
     */
    public Order addProductToOrder(String orderId, String productId, int quantity) {
        // TODO: Implement
        return null;
    }

    /**
     * Confirms an order (changes its status to CONFIRMED).
     *
     * TODO: 1. Find the order → if not found, throw ResourceNotFoundException.
     * 2. Validate that the order is in CREATED status → throw
     * BusinessRuleException if not. 3. Validate that the order has at least one
     * item → throw BusinessRuleException if not. 4. Change the status to
     * CONFIRMED and save. 5. Log at INFO level: "Order {orderId} confirmed with
     * {itemCount} items, total: {total}".
     */
    public Order confirmOrder(String orderId) {
        // TODO: Implement
        return null;
    }

    /**
     * Cancels an order (changes its status to CANCELLED).
     *
     * TODO: 1. Find the order → if not found, throw ResourceNotFoundException.
     * 2. Validate that the order is NOT in DELIVERED or CANCELLED status →
     * throw BusinessRuleException if it is already in one of those states. 3.
     * BONUS: Restore the stock of each product in the order items. 4. Change
     * status to CANCELLED and save. 5. Log at WARN level: "Order {orderId} has
     * been cancelled".
     */
    public Order cancelOrder(String orderId) {
        // TODO: Implement
        return null;
    }

    /**
     * Retrieves an order by its ID.
     *
     * TODO: 1. Find the order → if not found, throw ResourceNotFoundException.
     * 2. Log at DEBUG level: "Retrieved order: {orderId}". 3. Return the order.
     */
    public Order getOrder(String orderId) {
        // TODO: Implement
        return null;
    }
}
