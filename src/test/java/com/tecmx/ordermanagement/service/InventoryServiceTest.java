package com.tecmx.ordermanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tecmx.ordermanagement.model.Product;
import com.tecmx.ordermanagement.repository.OrderRepository;

/**
 * Tests for InventoryService.
 *
 * INSTRUCTIONS: - Complete each test according to the instructions. - AAA
 * pattern: Arrange → Act → Assert. - Verify exceptions with assertThrows(). -
 * Verify interactions with verify().
 */
@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product("PROD-001", "Laptop", 999.99, 10);
    }

    // =========================================================================
    // registerProduct tests
    // =========================================================================
    @Nested
    @DisplayName("registerProduct()")
    class RegisterProductTests {

        @Test
        @DisplayName("Should register a product successfully")
        void shouldRegisterProductSuccessfully() {
            // TODO: Arrange - Mock saveProduct to return the received argument.
            // TODO: Act - registerProduct("PROD-002", "Mouse", 29.99, 100).
            // TODO: Assert - The returned product has the correct data.
            //       Verify that saveProduct was invoked 1 time.
        }

        @Test
        @DisplayName("Should throw ValidationException if id is null")
        void shouldThrowValidationExceptionWhenIdIsNull() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw ValidationException if id is empty")
        void shouldThrowValidationExceptionWhenIdIsEmpty() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw ValidationException if name is null")
        void shouldThrowValidationExceptionWhenNameIsNull() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw ValidationException if price is <= 0")
        void shouldThrowValidationExceptionWhenPriceIsInvalid() {
            // TODO: Implement for price = 0 and price = -5.
        }

        @Test
        @DisplayName("Should throw ValidationException if stockQuantity < 0")
        void shouldThrowValidationExceptionWhenStockIsNegative() {
            // TODO: Implement
        }
    }

    // =========================================================================
    // restockProduct tests
    // =========================================================================
    @Nested
    @DisplayName("restockProduct()")
    class RestockProductTests {

        @Test
        @DisplayName("Should update the stock successfully")
        void shouldRestockSuccessfully() {
            // TODO: Arrange - Mock findProductById → sampleProduct (stock: 10).
            //       Mock saveProduct → returns the argument.
            // TODO: Act - restockProduct("PROD-001", 5).
            // TODO: Assert - The stock is 15.
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the product does not exist")
        void shouldThrowResourceNotFoundWhenProductDoesNotExist() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw ValidationException if additionalStock <= 0")
        void shouldThrowValidationExceptionWhenAdditionalStockIsInvalid() {
            // TODO: Implement
        }
    }

    // =========================================================================
    // checkStock tests
    // =========================================================================
    @Nested
    @DisplayName("checkStock()")
    class CheckStockTests {

        @Test
        @DisplayName("Should return the stock when the product exists")
        void shouldReturnStockWhenProductExists() {
            // TODO: Implement
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when the product does not exist")
        void shouldThrowResourceNotFoundWhenProductDoesNotExist() {
            // TODO: Implement
        }
    }
}
