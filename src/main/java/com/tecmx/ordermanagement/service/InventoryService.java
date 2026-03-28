package com.tecmx.ordermanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecmx.ordermanagement.model.Product;
import com.tecmx.ordermanagement.repository.OrderRepository;

/**
 * Inventory/product management service.
 *
 * Students must complete the implementation.
 */
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final OrderRepository orderRepository;

    public InventoryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Registers a new product in the inventory.
     *
     * TODO: 1. Validate that id is not null or empty → ValidationException. 2.
     * Validate that name is not null or empty → ValidationException. 3.
     * Validate that price > 0 → ValidationException. 4. Validate that
     * stockQuantity >= 0 → ValidationException. 5. Create the product, save it
     * in the repository. 6. Log at INFO level: "Product registered: {id} -
     * {name} (stock: {qty}, price: {price})". 7. Return the created product.
     */
    public Product registerProduct(String id, String name, double price, int stockQuantity) {
        // TODO: Implement
        return null;
    }

    /**
     * Updates the stock of an existing product.
     *
     * TODO: 1. Find the product → if not found, throw
     * ResourceNotFoundException. 2. Validate that additionalStock > 0 →
     * ValidationException. 3. Add additionalStock to the current stock. 4. Save
     * the updated product. 5. Log at INFO level: "Stock updated for
     * {productId}: +{additionalStock} → new stock: {newTotal}". 6. Return the
     * updated product.
     */
    public Product restockProduct(String productId, int additionalStock) {
        // TODO: Implement
        return null;
    }

    /**
     * Checks the current stock of a product.
     *
     * TODO: 1. Find the product → if not found, throw
     * ResourceNotFoundException. 2. Log at DEBUG level: "Stock check for
     * {productId}: {currentStock}". 3. Return the stock quantity.
     */
    public int checkStock(String productId) {
        // TODO: Implement
        return 0;
    }
}
