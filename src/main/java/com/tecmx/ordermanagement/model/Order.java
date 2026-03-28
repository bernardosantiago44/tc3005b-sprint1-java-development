package com.tecmx.ordermanagement.model;

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

    /**
     * TODO: Implement this method to calculate the order total. - Sum the
     * subtotals of all OrderItems. - Throw BusinessRuleException if the order
     * has no items. - Log the calculated total at INFO level.
     */
    public double getTotal() {
        // TODO: Implement
        return 0.0;
    }

    @Override
    public String toString() {
        return "Order{id='" + id + "', customerId='" + customerId + "', status=" + status
                + ", items=" + items.size() + ", createdAt=" + createdAt + "}";
    }
}
