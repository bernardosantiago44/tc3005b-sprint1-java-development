package com.tecmx.ordermanagement.service;

import com.tecmx.ordermanagement.exception.OrderManagementException;
import com.tecmx.ordermanagement.exception.ResourceNotFoundException;
import com.tecmx.ordermanagement.exception.ValidationException;
import com.tecmx.ordermanagement.model.Order;
import org.jetbrains.annotations.NotNull;
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
     * @param id
     * @param name
     * @param price
     * @param stockQuantity initial stock
     * @throws OrderManagementException if id is empty or name is empty or price is <= 0 or stockQuantity < 0
     */
    public Product registerProduct(String id, String name, double price, int stockQuantity) throws OrderManagementException {
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
     * @param productId product's id
     * @param additionalStock increment to current stock
     * @throws ResourceNotFoundException if a product with the given id does not exist
     * @throws ValidationException if additionalStock is less than or equal to zero
     */
    public Product restockProduct(String productId, int additionalStock) throws OrderManagementException {
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
     * Checks the current stock of a product with the given id.
     * @throws ResourceNotFoundException if not found
     */
    public int checkStock(String productId) throws ResourceNotFoundException {
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
    private static void validateIsPositive(@NotNull Number number, String fieldName) throws ValidationException {
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
    private static void validateIsPositiveOrZero(@NotNull Number number, String fieldName) throws ValidationException {
        if (number.doubleValue() < 0) {
            logger.error("{} should be zero or more: {}", fieldName, number.doubleValue());
            throw new ValidationException("Value should be greater than or zero", fieldName);
        }
    }
}
