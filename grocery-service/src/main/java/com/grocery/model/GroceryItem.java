package com.grocery.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grocery_items")
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name is required")
    @Column(unique = true)
    private String name;

    @Positive(message = "Price must be positive")
    private double price;

    @Min(value = 0, message = "Inventory cannot be negative")
    private int inventoryLevel;

    @NotBlank(message = "Category is required")
    private String category;

    private String description;
}
