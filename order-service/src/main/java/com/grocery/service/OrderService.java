package com.grocery.service;

import com.grocery.client.GroceryItemClient;
import com.grocery.model.Order;
import com.grocery.model.Order.OrderStatus;
import com.grocery.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final GroceryItemClient groceryItemClient;

    public OrderService(OrderRepository orderRepository, GroceryItemClient groceryItemClient) {
        this.orderRepository = orderRepository;
        this.groceryItemClient = groceryItemClient;
    }

    @Transactional
    public Order createOrder(Order order) {
        // Validate order
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one grocery item");
        }

        // Validate and update inventory
        double totalPrice = 0;
        for (Order.OrderItem item : order.getOrderItems()) {
            // Fetch the grocery item to validate
            Object groceryItem = groceryItemClient.getGroceryItem(item.getGroceryItemId());
            
            // Validate inventory availability
            Map<String, Object> itemDetails = (Map<String, Object>) groceryItem;
            int currentInventory = ((Number) itemDetails.get("inventoryLevel")).intValue();
            
            if (currentInventory < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient inventory for item: " + item.getGroceryItemId());
            }
            
            totalPrice += currentInventory * item.getQuantity();
            // Update inventory
            groceryItemClient.updateInventoryLevel(item.getGroceryItemId(), -item.getQuantity());
        }

        // Save the order
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        
        // Restore inventory for each item
        for (Order.OrderItem item : order.getOrderItems()) {
            groceryItemClient.updateInventoryLevel(item.getGroceryItemId(), item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
