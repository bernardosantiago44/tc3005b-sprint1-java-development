package com.tecmx.ordermanagement.repository;

import java.util.Optional;

import com.tecmx.ordermanagement.model.Order;
import com.tecmx.ordermanagement.model.Product;

/**
 * Repository interface. Simulates data access. Students must NOT modify this
 * interface.
 */
public interface OrderRepository {

    Optional<Order> findOrderById(String orderId);

    Order saveOrder(Order order);

    void deleteOrder(String orderId);

    Optional<Product> findProductById(String productId);

    Product saveProduct(Product product);

    boolean existsOrderById(String orderId);
}
