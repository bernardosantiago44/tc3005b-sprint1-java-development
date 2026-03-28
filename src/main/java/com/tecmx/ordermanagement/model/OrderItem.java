package com.tecmx.ordermanagement.model;

/**
 * Represents a line item within an order.
 */
public class OrderItem {

    private Product product;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * TODO: Implement this method to calculate the subtotal (price * quantity).
     * Should throw ValidationException if quantity <= 0. Should log the
     * calculation at DEBUG level.
     */
    public double getSubtotal() {
        // TODO: Implement
        return 0.0;
    }

    @Override
    public String toString() {
        return "OrderItem{product=" + product + ", quantity=" + quantity + "}";
    }
}
