package com.tecmx.ordermanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tecmx.ordermanagement.model.Order;
import com.tecmx.ordermanagement.model.Product;
import com.tecmx.ordermanagement.repository.OrderRepository;

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
            // TODO: Arrange - Configure the mock so that existsOrderById returns false
            //       and saveOrder returns the received order.
            // TODO: Act - Call createOrder("ORD-001", "CUST-001").
            // TODO: Assert - Verify the returned order is not null,
            //       that the ID and customerId are correct,
            //       and that saveOrder was invoked exactly 1 time.
        }

        @Test
        @DisplayName("Should throw ValidationException if orderId is null")
        void shouldThrowValidationExceptionWhenOrderIdIsNull() {
            // TODO: Use assertThrows to verify that ValidationException is thrown
            //       when null is passed as orderId.
        }

        @Test
        @DisplayName("Should throw ValidationException if orderId is empty")
        void shouldThrowValidationExceptionWhenOrderIdIsEmpty() {
            // TODO: Use assertThrows to verify that ValidationException is thrown
            //       when "" is passed as orderId.
        }

        @Test
        @DisplayName("Should throw ValidationException if customerId is null")
        void shouldThrowValidationExceptionWhenCustomerIdIsNull() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if an order with that ID already exists")
        void shouldThrowBusinessRuleExceptionWhenOrderAlreadyExists() {
            // TODO: Arrange - Configure existsOrderById to return true.
            // TODO: Act & Assert - Verify that BusinessRuleException is thrown.
            // TODO: Verify that saveOrder was NEVER invoked (verify with never()).
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
            // TODO: Act - addProductToOrder("ORD-001", "PROD-001", 3).
            // TODO: Assert - The order has 1 item, the product stock decreased to 7.
            //       Verify that saveOrder and saveProduct were invoked.
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the order does not exist")
        void shouldThrowResourceNotFoundWhenOrderDoesNotExist() {
            // TODO: Arrange - Mock findOrderById → returns Optional.empty().
            // TODO: Act & Assert - assertThrows(ResourceNotFoundException.class, ...).
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the product does not exist")
        void shouldThrowResourceNotFoundWhenProductDoesNotExist() {
            // TODO: Arrange - Mock findOrderById → returns sampleOrder,
            //       findProductById → returns Optional.empty().
            // TODO: Act & Assert
        }

        @Test
        @DisplayName("Should throw ValidationException if quantity is <= 0")
        void shouldThrowValidationExceptionWhenQuantityIsInvalid() {
            // TODO: Implement for quantity = 0 and quantity = -1.
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if there is insufficient stock")
        void shouldThrowBusinessRuleExceptionWhenInsufficientStock() {
            // TODO: Arrange - sampleProduct has stock 10, requesting 15.
            // TODO: Act & Assert - Verify BusinessRuleException.
            // TODO: Verify that saveOrder and saveProduct were NEVER invoked.
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
            // TODO: Arrange - Add an OrderItem to sampleOrder.
            //       Mock findOrderById → returns sampleOrder.
            //       Mock saveOrder → returns the argument.
            // TODO: Act - confirmOrder("ORD-001").
            // TODO: Assert - The order status is CONFIRMED.
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if the order is not in CREATED state")
        void shouldThrowBusinessRuleExceptionWhenOrderNotInCreatedState() {
            // TODO: Arrange - Change sampleOrder.setStatus(Order.Status.CONFIRMED).
            // TODO: Act & Assert
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if the order has no items")
        void shouldThrowBusinessRuleExceptionWhenOrderHasNoItems() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the order does not exist")
        void shouldThrowResourceNotFoundWhenOrderDoesNotExist() {
            // TODO: Implement
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
            // TODO: Implement
        }

        @Test
        @DisplayName("Should cancel an order in CONFIRMED state")
        void shouldCancelConfirmedOrder() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if the order is already DELIVERED")
        void shouldThrowBusinessRuleExceptionWhenOrderIsDelivered() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw BusinessRuleException if the order is already CANCELLED")
        void shouldThrowBusinessRuleExceptionWhenOrderIsAlreadyCancelled() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the order does not exist")
        void shouldThrowResourceNotFoundWhenOrderDoesNotExist() {
            // TODO: Implement
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
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when order does not exist")
        void shouldThrowResourceNotFoundWhenOrderDoesNotExist() {
            // TODO: Implement
        }
    }
}
