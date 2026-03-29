package com.tecmx.ordermanagement.service;

import com.tecmx.ordermanagement.exception.BusinessRuleException;
import com.tecmx.ordermanagement.exception.OrderManagementException;
import com.tecmx.ordermanagement.exception.ResourceNotFoundException;
import com.tecmx.ordermanagement.exception.ValidationException;
import com.tecmx.ordermanagement.model.OrderItem;
import com.tecmx.ordermanagement.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecmx.ordermanagement.model.Order;
import com.tecmx.ordermanagement.repository.OrderRepository;

import java.util.Optional;

/**
 * Main order management service.
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
     * @param orderId id of the new order.
     * @param customerId customer to whom the order belongs to.
     * @return The created order
     */
    public Order createOrder(String orderId, String customerId) throws OrderManagementException {
        validateStringContents(orderId, "orderId");
        validateStringContents(customerId, "customerId");
        validateOrderIdAvailable(orderId);

        Order order = new Order(orderId, customerId);
        this.orderRepository.saveOrder(order);
        logger.info("Order {} created for customer {}", orderId, customerId);

        return order;
    }

    /**
     * Adds a product to an existing order.
     * @param orderId id of the order.
     * @param productId id of the product.
     * @param quantity desired quantity of the product.
     * @throws OrderManagementException if no order or product exist, or if there is insufficient
     * quantity of the product.
     * @return The updated order
     */
    public Order addProductToOrder(String orderId, String productId, int quantity) throws OrderManagementException {
        Optional<Order> order = orderRepository.findOrderById(orderId);
        Optional<Product> product = orderRepository.findProductById(productId);

        validateOptionalValue(order, "order");
        validateOptionalValue(product, "product");
        validateQuantity(quantity);
        validateStock(product.get().getStockQuantity(), quantity);

        int remainingQuantity = product.get().getStockQuantity() - quantity;
        product.get().setStockQuantity(remainingQuantity);
        orderRepository.saveProduct(product.get());

        OrderItem orderItem = new OrderItem(product.get(), quantity);
        order.get().addItem(orderItem);

        logger.info("Added {}x {} to order {}", quantity, product.get().getName(), orderId);
        logger.debug("Remaining stock for {}: {}", productId, remainingQuantity);

        return order.get();
    }

    /**
     * Confirms an order and changes its status to CONFIRMED.
     */
    public Order confirmOrder(String orderId) {
        ensureOrderIdExists(orderId);

        Order order = orderRepository
                .findOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("", "orderId")); // Should never throw


        validateOrderStatus(order.getStatus(), Order.Status.CREATED);
        validateOrderItemCount(order.getItems().size());

        order.setStatus(Order.Status.CONFIRMED);
        orderRepository.saveOrder(order);

        logger.info("Order {} confirmed with {} items, total: {}", orderId, order.getItems().size(), order.getTotal());

        return order;
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

    /** Checks for null or empty strings for the provided value.
     * Logs the error before throwing.
     * @param value String to validate
     * @param fieldName Name of the field to be validated
     * @throws ValidationException when value is null or empty.
     */
    private static void validateStringContents(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            logger.error("Found null or empty value for field {}", fieldName);
            throw new ValidationException("Empty " + fieldName, "OrderService." + fieldName);
        }
    }

    /** Checks the provided orderId returns null in the orderRepository
     *
     * @param orderId Order id expected not to be present
     * @throws BusinessRuleException if the provided orderId exists in the repository.
     */
    private void validateOrderIdAvailable(String orderId) throws BusinessRuleException {
        if (this.orderRepository.findOrderById(orderId).isPresent()) {
            logger.error("Found duplicate orderId: {}", orderId);
            throw new BusinessRuleException("Found duplicate orderId " + orderId);
        }
    }

    /** Ensures the orderId exists in the repository.
     *
     * @param orderId orderId to check for
     * @throws ResourceNotFoundException if orderId does not exist in the repository.
     */
    private void ensureOrderIdExists(String orderId) throws ResourceNotFoundException {
        if (!orderRepository.existsOrderById(orderId)) {
            logger.error("Order {} not found.", orderId);
            throw new ResourceNotFoundException("Order with id " + orderId + " does not exist.", "orderId");
        }
    }

    /** Validates an optional-value object.
     *
     * @param optional Optional object to be validated.
     * @param fieldName The name of the object to be validated.
     * @throws ResourceNotFoundException if the underlying value is null.
     */
    private static void validateOptionalValue(Optional optional, String fieldName) throws ResourceNotFoundException {
        if (optional.isEmpty()) {
            logger.error("Found null when unwrapping an optional {} ", fieldName);
            throw new ResourceNotFoundException("Object not found", fieldName);
        }
    }

    /**
     * Validates the quantity is a natural number.
     * @param quantity desired quantity
     * @throws ValidationException if quantity is zero or negative
     */
    private static void validateQuantity(int quantity) throws ValidationException {
        if (quantity <= 0) {
            logger.error("Invalid quantity value: {}", quantity);
            throw new ValidationException("Quantity can not be negative", "quantity");
        }
    }

    /** Validates the stock of the product is enough
     * to cover the order.
     *
     * @param inventoryStock actual stock of the product.
     * @param neededStock requested quantity of the product in order.
     * @throws BusinessRuleException if the stock is less than the required stock.
     */
    private static void validateStock(int inventoryStock, int neededStock) throws BusinessRuleException {
        if (inventoryStock < neededStock) {
            logger.error("Insufficient stock: {} out of the {} needed", inventoryStock, neededStock);
            throw new BusinessRuleException("Insufficient stock");
        }
    }

    /** Checks the actual status of an order with the expected status.
     * @param actual The current status of the order.
     * @param expected The expected status of the order.
     * @throws BusinessRuleException If both values are different.
     */
    private static void validateOrderStatus(Order.Status actual, Order.Status expected) throws BusinessRuleException {
        if (!actual.equals(expected)) {
            logger.error("Order status {} does not equal {}", actual, expected);
            throw new BusinessRuleException("Order status should be " + expected.toString());
        }
    }

    /** Validates the size to be a positive integer greater than zero.
     *
     * @param size Count of the order items.
     * @throws BusinessRuleException if size <= 0
     */
    private static void validateOrderItemCount(int size) throws BusinessRuleException {
        if (size <= 0) {
            logger.error("Order items can not be empty (provided {})", size);
            throw new BusinessRuleException("Order items can not be empty.");
        }
    }
}
