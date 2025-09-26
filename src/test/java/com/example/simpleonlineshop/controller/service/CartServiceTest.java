package com.example.simpleonlineshop.controller.service;

import com.example.simpleonlineshop.data.CartItemRepository;
import com.example.simpleonlineshop.entity.CartItem;
import com.example.simpleonlineshop.entity.Product;
import com.example.simpleonlineshop.entity.User;
import com.example.simpleonlineshop.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("john");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
    }

    @Test
    void testAddToCart_NewItem() {
        when(cartItemRepository.findByUserAndProduct(user, product)).thenReturn(Optional.empty());

        cartService.addToCart(user, product, 2);

        ArgumentCaptor<CartItem> captor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemRepository).save(captor.capture());
        CartItem savedItem = captor.getValue();

        assertEquals(user, savedItem.getUser());
        assertEquals(product, savedItem.getProduct());
        assertEquals(2, savedItem.getQuantity());
    }

    @Test
    void testAddToCart_ExistingItem() {
        CartItem existingItem = new CartItem();
        existingItem.setUser(user);
        existingItem.setProduct(product);
        existingItem.setQuantity(1);

        when(cartItemRepository.findByUserAndProduct(user, product)).thenReturn(Optional.of(existingItem));

        cartService.addToCart(user, product, 3);

        assertEquals(4, existingItem.getQuantity()); // 1 + 3
        verify(cartItemRepository).save(existingItem);
    }

    @Test
    void testGetCartItems() {
        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();

        when(cartItemRepository.findByUser(user)).thenReturn(Arrays.asList(item1, item2));

        List<CartItem> items = cartService.getCartItems(user);

        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }

    @Test
    void testRemoveFromCart_Success() {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setId(1L);

        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

        boolean result = cartService.removeFromCart(1L, "john");

        assertTrue(result);
        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    void testRemoveFromCart_Failure_WrongUser() {
        CartItem cartItem = new CartItem();
        User otherUser = new User();
        otherUser.setUsername("jane");
        cartItem.setUser(otherUser);
        cartItem.setId(1L);

        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

        boolean result = cartService.removeFromCart(1L, "john");

        assertFalse(result);
        verify(cartItemRepository, never()).delete(any());
    }

    @Test
    void testClearCart() {
        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();

        when(cartItemRepository.findByUser(user)).thenReturn(Arrays.asList(item1, item2));

        cartService.clearCart(user);

        verify(cartItemRepository).deleteAll(Arrays.asList(item1, item2));
    }
}