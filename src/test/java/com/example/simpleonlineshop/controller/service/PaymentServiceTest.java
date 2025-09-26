package com.example.simpleonlineshop.controller.service;

import com.example.simpleonlineshop.data.PaymentRepository;
import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.entity.Payment;
import com.example.simpleonlineshop.entity.User;
import com.example.simpleonlineshop.service.OrderService;
import com.example.simpleonlineshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentService paymentService;

    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("john");

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setTotal(new BigDecimal("100.00"));
        order.setStatus("PENDING");
    }

    @Test
    void testProcessPayment_Success() {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = paymentService.processPayment(1L, "john");

        assertTrue(result);

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        Payment savedPayment = paymentCaptor.getValue();

        assertEquals(order, savedPayment.getOrder());
        assertEquals(order.getTotal(), savedPayment.getAmount());
        assertEquals("COMPLETED", savedPayment.getStatus());

        assertEquals("PAID", order.getStatus());
        verify(orderService).saveOrder(order);
    }

    @Test
    void testProcessPayment_OrderNotFound() {
        when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

        boolean result = paymentService.processPayment(1L, "john");

        assertFalse(result);
        verify(paymentRepository, never()).save(any());
        verify(orderService, never()).saveOrder(any());
    }

    @Test
    void testProcessPayment_WrongUser() {
        User otherUser = new User();
        otherUser.setUsername("jane");
        order.setUser(otherUser);

        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        boolean result = paymentService.processPayment(1L, "john");

        assertFalse(result);
        verify(paymentRepository, never()).save(any());
        verify(orderService, never()).saveOrder(any());
    }

    @Test
    void testProcessPayment_OrderNotPending() {
        order.setStatus("PAID");
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        boolean result = paymentService.processPayment(1L, "john");

        assertFalse(result);
        verify(paymentRepository, never()).save(any());
        verify(orderService, never()).saveOrder(any());
    }
}
