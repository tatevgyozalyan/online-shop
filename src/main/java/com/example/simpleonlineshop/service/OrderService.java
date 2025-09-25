package com.example.simpleonlineshop.service;

import com.example.simpleonlineshop.data.OrderItemRepository;
import com.example.simpleonlineshop.data.OrderRepository;
import com.example.simpleonlineshop.entity.CartItem;
import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.entity.OrderItem;
import com.example.simpleonlineshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Transactional
    public Order createOrderFromCart(User user) {
        List<CartItem> cartItems = cartService.getCartItems(user);
        if (cartItems.isEmpty()) {
            return null;
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotal(BigDecimal.ZERO);

        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);

            total = total.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }

        order.setTotal(total);
        orderRepository.save(order);

        cartService.clearCart(user);

        return order;
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }

    //get rid of this
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Optional<Order> getOrderByIdAndUsername(Long orderId, String username) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent() &&
                optionalOrder.get().getUser().getUsername().equals(username)) {
            return optionalOrder;
        }
        return Optional.empty();

    }
}
