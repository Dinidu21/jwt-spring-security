package com.example.practice_add.controller;

import com.example.practice_add.entity.Order;
import com.example.practice_add.entity.User;
import com.example.practice_add.repo.OrderRepository;
import com.example.practice_add.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all orders (admin only)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get user's own orders (user or admin)
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Order> getMyOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderRepository.findByUserId(user.getId());
    }

    // Create order (user or admin, but tied to current user)
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Order createOrder(@RequestBody Order order, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        order.setUser(user);
        return orderRepository.save(order);
    }

    // Update order (admin or owner)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderRepository.findById(#id).get().getUser().getId() == authentication.principal.id")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setProductName(orderDetails.getProductName());
        order.setPrice(orderDetails.getPrice());
        return ResponseEntity.ok(orderRepository.save(order));
    }

    // Delete order (admin or owner)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderRepository.findById(#id).get().getUser().getId() == authentication.principal.id")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
