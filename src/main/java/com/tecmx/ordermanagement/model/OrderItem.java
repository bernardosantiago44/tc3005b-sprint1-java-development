package com.tecmx.ordermanagement.model;

import com.tecmx.ordermanagement.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a line item within an order.
 */
public class OrderItem {

    private static final Logger logger = LoggerFactory.getLogger(OrderItem.class);

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
        validateQuantity(this.quantity);

        double subtotal = this.product.getPrice() * (double)this.quantity;
        logger.debug("Price x Quantity: {} x {} = {}", this.product.getPrice(), this.quantity, subtotal);

        return subtotal;
    }

    private static void validateQuantity(int quantity) throws ValidationException {
        if (quantity <= 0) {
            logger.error("Invalid Quantity {}", quantity);
            throw new ValidationException("Quantity must be greater than zero.", "Product.Quantity");
        }
    }

    @Override
    public String toString() {
        return "OrderItem{product=" + product + ", quantity=" + quantity + "}";
    }
}
