package com.tecmx.ordermanagement.service;

import com.tecmx.ordermanagement.exception.ResourceNotFoundException;
import com.tecmx.ordermanagement.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecmx.ordermanagement.model.Product;
import com.tecmx.ordermanagement.repository.OrderRepository;

/**
 * Inventory/product management service.
 */
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final OrderRepository orderRepository;

    public InventoryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Registers a new product in the inventory.
     */
    public Product registerProduct(String id, String name, double price, int stockQuantity) {
        validateStringNotEmpty(id, "productId");
        validateStringNotEmpty(name, "productName");
        validateIsPositive(price, "price");
        validateIsPositiveOrZero(stockQuantity, "stockQuantity");

        Product product = new Product(id, name, price, stockQuantity);
        orderRepository.saveProduct(product);

        logger.info("Product registered: {}, {}, (stock: {}, price: {})", id, name, stockQuantity, price);

        return product;
    }

    /**
     * Updates the stock of an existing product.
     */
    public Product restockProduct(String productId, int additionalStock) {
        Product product = orderRepository
                .findProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found", "productId"));

        validateIsPositive(additionalStock, "additionalStock");

        int newTotal = product.getStockQuantity() + additionalStock;
        product.setStockQuantity(newTotal);

        logger.info("Stock updated for {}: +{} → new stock: {}", productId, additionalStock, newTotal);

        return product;
    }

    /**
     * Checks the current stock of a product.
     */
    public int checkStock(String productId) {
        Product product = orderRepository
                .findProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found", "productId"));

        logger.debug("Stock checked for {}: {}", product, product.getStockQuantity());

        return product.getStockQuantity();
    }

    /** Validates the given string not to be null or empty.
     *
     * @param string    to be validated
     * @param fieldName name of the field being validated
     * @throws ValidationException if the string is null or empty.
     */
    private static void validateStringNotEmpty(String string, String fieldName) throws ValidationException {
        if (string == null || string.trim().isEmpty()) {
            logger.error("Found empty {} string", fieldName);
            throw new ValidationException("Found null or empty string", fieldName);
        }
    }

    /** Validates the given number to be greater than zero.
     *
     * @param number number to be validated (can be int or double)
     * @param fieldName name of the value being validated
     * @throws ValidationException if number <= 0
     */
    private static void validateIsPositive(Number number, String fieldName) throws ValidationException {
        if (number.doubleValue() <= 0) {
            logger.error("{} should be greater than zero: {}", fieldName, number.doubleValue());
            throw new ValidationException("Value should be greater than zero", fieldName);
        }
    }

    /** Validates the given number to be zero or greater.
     *
     * @param number number to be validated (can be int or double)
     * @param fieldName name of the value being validated
     * @throws ValidationException if number < 0
     */
    private static void validateIsPositiveOrZero(Number number, String fieldName) throws ValidationException {
        if (number.doubleValue() < 0) {
            logger.error("{} should be zero or more: {}", fieldName, number.doubleValue());
            throw new ValidationException("Value should be greater than or zero", fieldName);
        }
    }
}
