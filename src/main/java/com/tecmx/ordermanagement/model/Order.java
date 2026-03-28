package com.tecmx.ordermanagement.model;

import com.tecmx.ordermanagement.exception.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a purchase order.
 */
public class Order {

    public enum Status {
        CREATED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    private String id;
    private String customerId;
    private List<OrderItem> items;
    private Status status;
    private LocalDateTime createdAt;

    public Order() {
        this.items = new ArrayList<>();
        this.status = Status.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public Order(String id, String customerId) {
        this();
        this.id = id;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    /** Validates and calculates order total. */
    public double getTotal() {
        validateOrderItemCount();

        double total = this.items
                .stream()
                .map((OrderItem::getSubtotal))
                .reduce(0.0, Double::sum);
        logger.info("Order id: {}, Order Total: {}", this.id, total);

        return total;
    }

    /** Validates that order count
     * is greater than zero.
     * @throws BusinessRuleException if items is empty.
    */
    private void validateOrderItemCount() throws BusinessRuleException {
        if (this.items.isEmpty()) {
            logger.error("Order items is empty.");
            throw new BusinessRuleException("Invalid order item count: " + this.items.size());
        }
    }

    @Override
    public String toString() {
        return "Order{id='" + id + "', customerId='" + customerId + "', status=" + status
                + ", items=" + items.size() + ", createdAt=" + createdAt + "}";
    }
}
