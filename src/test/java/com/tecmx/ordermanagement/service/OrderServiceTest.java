package com.tecmx.ordermanagement.service;

import com.tecmx.ordermanagement.exception.BusinessRuleException;
import com.tecmx.ordermanagement.exception.ResourceNotFoundException;
import com.tecmx.ordermanagement.exception.ValidationException;
import com.tecmx.ordermanagement.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tecmx.ordermanagement.model.Order;
import com.tecmx.ordermanagement.model.Product;
import com.tecmx.ordermanagement.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for OrderService.
 *
 * GENERAL INSTRUCTIONS: - Complete each test following the TODO instructions. -
 * Use @Mock for OrderRepository and @InjectMocks for OrderService. - Each test
 * must follow the AAA pattern: Arrange → Act → Assert. - Verify that the
 * correct exceptions are thrown with assertThrows(). - Use verify() to confirm
 * interactions with the mocked repository.
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Product sampleProduct;
    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product("PROD-001", "Laptop", 999.99, 10);
        sampleOrder = new Order("ORD-001", "CUST-001");
    }

    // =========================================================================
    // createOrder tests
    // =========================================================================
    @Nested
    @DisplayName("createOrder()")
    class CreateOrderTests {

        @Test
        @DisplayName("Should create an order successfully with valid data")
        void shouldCreateOrderSuccessfully() {
            // TODO: Arrange - Configure the mock so that existsOrderById returns false (shouldn't it be true?)
            //       and saveOrder returns the received order.
            when(orderRepository.existsOrderById("ORD-001")).thenReturn(false);
            when(orderRepository.saveOrder(any(Order.class))).thenAnswer(
                    invocation -> invocation.getArgument(0)
            );
            
            // TODO: Act - Call createOrder("ORD-001", "CUST-001").
            Order createdOrder = orderService.createOrder("ORD-001", "CUST-001");
            
            // TODO: Assert - Verify the returned order is not null,
            //       that the ID and customerId are correct,
            //       and that saveOrder was invoked exactly 1 time.
            assertNotNull(createdOrder);
            assertEquals("ORD-001", createdOrder.getId());
            assertEquals("CUST-001", createdOrder.getCustomerId());
            verify(orderRepository, times(1)).saveOrder(createdOrder);
        }

        @Test
        @DisplayName("Should throw ValidationException if orderId is null")
        void shouldThrowValidationExceptionWhenOrderIdIsNull() {
            assertThrows(ValidationException.class, () -> {
                orderService.createOrder(null, "CUST-001");
            });
        }

        @Test
        @DisplayName("Should throw ValidationException if orderId is empty")
        void shouldThrowValidationExceptionWhenOrderIdIsEmpty() {
            assertThrows(ValidationException.class, () -> {
                orderService.createOrder("", "CUST-001");
            });
        }

        @Test
        @DisplayName("Should throw ValidationException if customerId is null")
        void shouldThrowValidationExceptionWhenCustomerIdIsNull() {
            assertThrows(ValidationException.class, () -> {
                orderService.createOrder("ORD-100", null);
            });
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if an order with that ID already exists")
        void shouldThrowBusinessRuleExceptionWhenOrderAlreadyExists() {
            // TODO: Arrange - Configure existsOrderById to return true.
            when(orderRepository.existsOrderById("ORD-001")).thenReturn(true);
            // TODO: Act & Assert - Verify that BusinessRuleException is thrown.
            assertThrows(BusinessRuleException.class, () -> {
                orderService.createOrder("ORD-001", "CUST-001");
            });
            
            // TODO: Verify that saveOrder was NEVER invoked (verify with never()).
            verify(orderRepository, never()).saveOrder(new Order("ORD-001", "CUST-001"));
        }
    }

    // =========================================================================
    // addProductToOrder tests
    // =========================================================================
    @Nested
    @DisplayName("addProductToOrder()")
    class AddProductToOrderTests {

        @Test
        @DisplayName("Should add a product to the order successfully")
        void shouldAddProductToOrderSuccessfully() {
            // TODO: Arrange - Mock findOrderById → returns sampleOrder,
            //       findProductById → returns sampleProduct (stock: 10).
            //       saveOrder and saveProduct return the received argument.
            when(orderRepository.findOrderById("ORD-001")).thenReturn(Optional.ofNullable(sampleOrder));
            when(orderRepository.findProductById("PROD-001")).thenReturn(Optional.ofNullable(sampleProduct));
            when(orderRepository.saveOrder(sampleOrder)).thenReturn(sampleOrder);
            when(orderRepository.saveProduct(sampleProduct)).thenReturn(sampleProduct);
            // TODO: Act - addProductToOrder("ORD-001", "PROD-001", 3).
            Order order = orderService.addProductToOrder("ORD-001", "PROD-001", 3);
            orderRepository.saveOrder(order);
            // TODO: Assert - The order has 1 item, the product stock decreased to 7.
            //       Verify that saveOrder and saveProduct were invoked.
            assertEquals(1, order.getItems().size());
            assertEquals(7, orderRepository.findProductById("PROD-001").get().getStockQuantity());
            verify(orderRepository, times(1)).saveOrder(order);
            verify(orderRepository, times(1)).saveProduct(order.getItems().get(0).getProduct());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the order does not exist")
        void shouldThrowResourceNotFoundWhenOrderDoesNotExist() {
            when(orderRepository.findOrderById(any(String.class))).thenReturn(Optional.empty());
            
            ResourceNotFoundException error = assertThrows(ResourceNotFoundException.class, () -> orderService.addProductToOrder("ORD-001", "PROD-001", 1));
            assertEquals("order", error.getResourceId());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the product does not exist")
        void shouldThrowResourceNotFoundWhenProductDoesNotExist() {
            when(orderRepository.findOrderById(any())).thenReturn(Optional.ofNullable(sampleOrder));
            when(orderRepository.findProductById(any())).thenReturn(Optional.empty());
            
            ResourceNotFoundException error = assertThrows(ResourceNotFoundException.class, () -> orderService.addProductToOrder("ORD-001", "PROD-001", 1));
            assertEquals("product", error.getResourceId());
        }

        @Test
        @DisplayName("Should throw ValidationException if quantity is <= 0")
        void shouldThrowValidationExceptionWhenQuantityIsInvalid() {
            when(orderRepository.findOrderById(any())).thenReturn(Optional.ofNullable(sampleOrder));
            when(orderRepository.findProductById(any())).thenReturn(Optional.ofNullable(sampleProduct));
            
            assertThrows(ValidationException.class, () -> orderService.addProductToOrder("ORD-001", "PROD-001", 0));
            assertThrows(ValidationException.class, () -> orderService.addProductToOrder("ORD-001", "PROD-001", -1));
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if there is insufficient stock")
        void shouldThrowBusinessRuleExceptionWhenInsufficientStock() {
            // TODO: Arrange - sampleProduct has stock 10, requesting 15.
            when(orderRepository.findOrderById(any())).thenReturn(Optional.ofNullable(sampleOrder));
            when(orderRepository.findProductById(any())).thenReturn(Optional.ofNullable(sampleProduct));
            // TODO: Act & Assert - Verify BusinessRuleException.
            assertThrows(BusinessRuleException.class, () -> orderService.addProductToOrder("ORD-001", "PROD-001", 15));
            // TODO: Verify that saveOrder and saveProduct were NEVER invoked.
            verify(orderRepository, never()).saveOrder(any());
        }
    }

    // =========================================================================
    // confirmOrder tests
    // =========================================================================
    @Nested
    @DisplayName("confirmOrder()")
    class ConfirmOrderTests {

        @Test
        @DisplayName("Should confirm a valid order with items")
        void shouldConfirmOrderSuccessfully() {
            OrderItem item = new OrderItem(sampleProduct, 3);
            sampleOrder.addItem(item);
            
            when(orderRepository.findOrderById(any())).thenReturn(Optional.of(sampleOrder));
            when(orderRepository.saveOrder(any(Order.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
            
            Order order = orderService.confirmOrder("ORD-001");
            
            assertEquals(Order.Status.CONFIRMED, order.getStatus());
            verify(orderRepository, times(1)).saveOrder(sampleOrder);
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if the order is not in CREATED state")
        void shouldThrowBusinessRuleExceptionWhenOrderNotInCreatedState() {
            sampleOrder.setStatus(Order.Status.CONFIRMED);
            when(orderRepository.findOrderById(any())).thenReturn(Optional.of(sampleOrder));
            
            assertThrows(BusinessRuleException.class, () -> orderService.confirmOrder("ORD-001"));
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if the order has no items")
        void shouldThrowBusinessRuleExceptionWhenOrderHasNoItems() {
            sampleOrder.setItems(new ArrayList<>());
            when(orderRepository.findOrderById(any())).thenReturn(Optional.of(sampleOrder));
            
            assertThrows(BusinessRuleException.class, () -> orderService.confirmOrder("ord-001"));
            verify(orderRepository, never()).saveOrder(any());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the order does not exist")
        void shouldThrowResourceNotFoundWhenOrderDoesNotExist() {
            when(orderRepository.findOrderById(any())).thenReturn(Optional.empty());
            ResourceNotFoundException error = assertThrows(ResourceNotFoundException.class, () -> orderService.confirmOrder("ORD-001"));
            assertEquals("orderId", error.getResourceId());
            verify(orderRepository, never()).saveOrder(any());
        }
    }

    // =========================================================================
    // cancelOrder tests
    // =========================================================================
    @Nested
    @DisplayName("cancelOrder()")
    class CancelOrderTests {

        @Test
        @DisplayName("Should cancel an order in CREATED state")
        void shouldCancelCreatedOrder() {
            sampleOrder.setStatus(Order.Status.CREATED);
            when(orderRepository.findOrderById(any())).thenReturn(Optional.of(sampleOrder));
            
            Order order = orderService.cancelOrder("ORD-001");
            
            assertEquals(Order.Status.CANCELLED, order.getStatus());
        }

        @Test
        @DisplayName("Should cancel an order in CONFIRMED state")
        void shouldCancelConfirmedOrder() {
            sampleOrder.setStatus(Order.Status.CONFIRMED);
            when(orderRepository.findOrderById(any())).thenReturn(Optional.of(sampleOrder));
            
            Order order = orderService.cancelOrder("ORD-001");
            
            assertEquals(Order.Status.CANCELLED, order.getStatus());
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if the order is already DELIVERED")
        void shouldThrowBusinessRuleExceptionWhenOrderIsDelivered() {
            sampleOrder.setStatus(Order.Status.DELIVERED);
            when(orderRepository.findOrderById(any())).thenReturn(Optional.of(sampleOrder));
            
            assertThrows(BusinessRuleException.class, () -> orderService.cancelOrder("ORD-001"));
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if the order is already CANCELLED")
        void shouldThrowBusinessRuleExceptionWhenOrderIsAlreadyCancelled() {
            sampleOrder.setStatus(Order.Status.CANCELLED);
            when(orderRepository.findOrderById(any())).thenReturn(Optional.of(sampleOrder));
            
            assertThrows(BusinessRuleException.class, () -> orderService.cancelOrder("ORD-001"));
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the order does not exist")
        void shouldThrowResourceNotFoundWhenOrderDoesNotExist() {
            when(orderRepository.findOrderById(any())).thenReturn(Optional.empty());
            
            ResourceNotFoundException error = assertThrows(ResourceNotFoundException.class, () -> orderService.cancelOrder("ORD-001"));
            assertEquals("orderId", error.getResourceId());
        }
    }

    // =========================================================================
    // getOrder tests
    // =========================================================================
    @Nested
    @DisplayName("getOrder()")
    class GetOrderTests {

        @Test
        @DisplayName("Should return the order when it exists")
        void shouldReturnOrderWhenFound() {
            when(orderRepository.findOrderById(any())).thenReturn(Optional.of(sampleOrder));
            assertNotNull(orderService.getOrder("ORD-001"));
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when order does not exist")
        void shouldThrowResourceNotFoundWhenOrderDoesNotExist() {
            when(orderRepository.findOrderById(any())).thenReturn(Optional.empty());
            ResourceNotFoundException error = assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder("ORD-001"));
            assertEquals("orderId", error.getResourceId());
        }
    }
}
