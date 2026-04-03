package com.tecmx.ordermanagement.service;

import com.tecmx.ordermanagement.exception.ResourceNotFoundException;
import com.tecmx.ordermanagement.exception.ValidationException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
            when(orderRepository.saveProduct(any(Product.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
            // TODO: Act - registerProduct("PROD-002", "Mouse", 29.99, 100).
            Product product = inventoryService.registerProduct("PROD-002", "Mouse", 29.99, 100);
            // TODO: Assert - The returned product has the correct data.
            //       Verify that saveProduct was invoked 1 time.
            assertEquals("PROD-002", product.getId());
            assertEquals("Mouse", product.getName());
            assertEquals(29.99, product.getPrice());
            assertEquals(100, product.getStockQuantity());
            verify(orderRepository, times(1)).saveProduct(any());
        }

        @Test
        @DisplayName("Should throw ValidationException if id is null")
        void shouldThrowValidationExceptionWhenIdIsNull() {
            var error = assertThrows(ValidationException.class, () -> inventoryService.registerProduct(null, "Mouse", 29.99, 100));
            assertEquals("productId", error.getFieldName());
        }

        @Test
        @DisplayName("Should throw ValidationException if id is empty")
        void shouldThrowValidationExceptionWhenIdIsEmpty() {
            var error = assertThrows(ValidationException.class, () -> inventoryService.registerProduct("", "Mouse", 29.99, 100));
            assertEquals("productId", error.getFieldName());
        }

        @Test
        @DisplayName("Should throw ValidationException if name is null")
        void shouldThrowValidationExceptionWhenNameIsNull() {
            var error = assertThrows(ValidationException.class, () -> inventoryService.registerProduct("PROD-002", null, 29.99, 100));
            assertEquals("productName", error.getFieldName());
        }
        
        @Test
        @DisplayName("Should throw ValidationException if name is empty")
        void shouldThrowValidationExceptionWhenNameIsEmpty() {
            var error = assertThrows(ValidationException.class, () -> inventoryService.registerProduct("PROD-002", "", 29.99, 100));
            assertEquals("productName", error.getFieldName());
        }

        @Test
        @DisplayName("Should throw ValidationException if price is <= 0")
        void shouldThrowValidationExceptionWhenPriceIsInvalid() {
            var zero = assertThrows(ValidationException.class, () -> inventoryService.registerProduct("PROD-002", "Mouse", 0, 100));
            var negative = assertThrows(ValidationException.class, () -> inventoryService.registerProduct("PROD-002", "Mouse", -5, 100));
            assertEquals("price", zero.getFieldName());
            assertEquals("price", negative.getFieldName());
            assertEquals("VALIDATION_ERROR", zero.getErrorCode());
        }

        @Test
        @DisplayName("Should throw ValidationException if stockQuantity < 0")
        void shouldThrowValidationExceptionWhenStockIsNegative() {
            var negative = assertThrows(ValidationException.class, () -> inventoryService.registerProduct("PROD-002", "Mouse", 29.99, -5));
            assertEquals("stockQuantity", negative.getFieldName());
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
            when(orderRepository.findProductById(any())).thenReturn(Optional.of(sampleProduct));
            when(orderRepository.saveProduct(any(Product.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0)); // When not implemented, this fails
            
            Product product = inventoryService.restockProduct("PROD-001", 5);
            
            assertEquals(15, product.getStockQuantity());
            verify(orderRepository, times(1)).saveProduct(sampleProduct);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException if the product does not exist")
        void shouldThrowResourceNotFoundWhenProductDoesNotExist() {
            when(orderRepository.findProductById(any())).thenReturn(Optional.empty());
            
            var error = assertThrows(ResourceNotFoundException.class, () -> inventoryService.restockProduct("PROD-001", 5));
            assertEquals("productId", error.getResourceId());
        }

        @Test
        @DisplayName("Should throw ValidationException if additionalStock <= 0")
        void shouldThrowValidationExceptionWhenAdditionalStockIsInvalid() {
            // TODO: Implement
            when(orderRepository.findProductById(any())).thenReturn(Optional.of(sampleProduct));
            
            var zero = assertThrows(ValidationException.class, () ->  inventoryService.restockProduct("PROD-001", 0));
            var negative = assertThrows(ValidationException.class, () ->  inventoryService.restockProduct("PROD-001", -1));
            
            verify(orderRepository, never()).saveProduct(sampleProduct);
            assertEquals("VALIDATION_ERROR", zero.getErrorCode());
            assertEquals("additionalStock", zero.getFieldName());
            assertEquals("additionalStock", negative.getFieldName());
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
            when(orderRepository.findProductById(any())).thenReturn(Optional.of(sampleProduct));
            assertEquals(10, inventoryService.checkStock("PROD-001"));
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when the product does not exist")
        void shouldThrowResourceNotFoundWhenProductDoesNotExist() {
            // TODO: Implement
            when(orderRepository.findProductById(any())).thenReturn(Optional.empty());
            var error = assertThrows(ResourceNotFoundException.class, () -> inventoryService.checkStock("PROD-001"));
            assertEquals("productId", error.getResourceId());
        }
    }
}
