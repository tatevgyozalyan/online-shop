package com.example.simpleonlineshop.service;

import com.example.simpleonlineshop.data.CartItemRepository;
import com.example.simpleonlineshop.entity.CartItem;
import com.example.simpleonlineshop.entity.Product;
import com.example.simpleonlineshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addToCart(User user, Product product, int quantity) {
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElse(new CartItem());
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public boolean removeFromCart(Long cartItemId, String username) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (cartItem.getUser().getUsername().equals(username)) {
                cartItemRepository.delete(cartItem);
                return true;
            }
        }
        return false;
    }


    public void clearCart(User user) {
        List<CartItem> items = getCartItems(user);
        cartItemRepository.deleteAll(items);
    }
}
