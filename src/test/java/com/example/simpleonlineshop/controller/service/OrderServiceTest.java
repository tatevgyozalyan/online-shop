package com.example.simpleonlineshop.controller.service;

import com.example.simpleonlineshop.data.OrderItemRepository;
import com.example.simpleonlineshop.data.OrderRepository;
import com.example.simpleonlineshop.entity.CartItem;
import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.entity.Product;
import com.example.simpleonlineshop.entity.User;
import com.example.simpleonlineshop.service.CartService;
import com.example.simpleonlineshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Product product1;
    private Product product2;

    OrderServiceTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("john");

        product1 = new Product();
        product1.setId(1L);
        product1.setPrice(new BigDecimal("10.00"));

        product2 = new Product();
        product2.setId(2L);
        product2.setPrice(new BigDecimal("20.00"));
    }

    @Test
    void testCreateOrderFromCart_WithItems() {
        CartItem item1 = new CartItem();
        item1.setProduct(product1);
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        item2.setProduct(product2);
        item2.setQuantity(1);

        when(cartService.getCartItems(user)).thenReturn(Arrays.asList(item1, item2));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrderFromCart(user);

        assertNotNull(order);
        assertEquals(user, order.getUser());
        assertEquals("PENDING", order.getStatus());
        assertEquals(new BigDecimal("40.00"), order.getTotal()); // (2*10) + (1*20)

        verify(orderItemRepository, times(2)).save(any());
        verify(cartService).clearCart(user);
        verify(orderRepository, times(2)).save(any(Order.class)); // first to create, second to update total
    }

    @Test
    void testCreateOrderFromCart_EmptyCart() {
        when(cartService.getCartItems(user)).thenReturn(List.of());

        Order order = orderService.createOrderFromCart(user);

        assertNull(order);
        verify(orderRepository, never()).save(any());
        verify(orderItemRepository, never()).save(any());
        verify(cartService, never()).clearCart(user);
    }

    @Test
    void testGetUserOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findByUser(user)).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getUserOrders(user);

        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    void testGetOrderByIdAndUsername_Success() {
        Order order = new Order();
        order.setUser(user);
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderByIdAndUsername(1L, "john");

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    void testGetOrderByIdAndUsername_Failure_WrongUser() {
        User otherUser = new User();
        otherUser.setUsername("jane");

        Order order = new Order();
        order.setUser(otherUser);
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderByIdAndUsername(1L, "john");

        assertTrue(result.isEmpty());
    }
}