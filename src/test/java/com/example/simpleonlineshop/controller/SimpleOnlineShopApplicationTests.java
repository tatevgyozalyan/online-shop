package com.example.simpleonlineshop.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.simpleonlineshop.controller.PaymentController;
import com.example.simpleonlineshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class SimpleOnlineShopApplicationTests {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void paymentForm_ShouldReturnPaymentView() throws Exception {
        mockMvc.perform(get("/payment/{orderId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("payment")); // returns payment.html
    }

//    @Test
//    void processPayment_ShouldCallServiceAndRedirect() throws Exception {
//        mockMvc.perform(post("/payment/process/{orderId}", 1L))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/orders"));
//
//        verify(paymentService, times(1)).processPayment(1L);
//    }

}
