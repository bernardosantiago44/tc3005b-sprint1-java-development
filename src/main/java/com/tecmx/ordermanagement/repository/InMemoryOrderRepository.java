package com.tecmx.ordermanagement.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.tecmx.ordermanagement.model.Order;
import com.tecmx.ordermanagement.model.Product;

/**
 * In-memory implementation of the repository. Simulates a database using
 * HashMaps.
 */
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> orders = new HashMap<>();
    private final Map<String, Product> products = new HashMap<>();

    @Override
    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    @Override
    public Order saveOrder(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public void deleteOrder(String orderId) {
        orders.remove(orderId);
    }

    @Override
    public Optional<Product> findProductById(String productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public Product saveProduct(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public boolean existsOrderById(String orderId) {
        return orders.containsKey(orderId);
    }

    // Helper to pre-load data in tests
    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }
}
