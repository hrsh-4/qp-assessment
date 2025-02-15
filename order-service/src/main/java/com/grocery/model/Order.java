package com.grocery.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grocery_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User ID is required")
    private String userId;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderItems;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
    }

    @Getter
    @Setter
    @Embeddable
    public static class OrderItem {
        @Positive(message = "Grocery item ID is required")
        private Long groceryItemId;

        @Min(value = 1, message = "Quantity must be at least 1")
        private int quantity;
    }

    public enum OrderStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }
}
