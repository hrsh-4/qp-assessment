package com.grocery.repository;

import com.grocery.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
    Optional<GroceryItem> findByName(String name);
    List<GroceryItem> findByCategory(String category);
    List<GroceryItem> findByInventoryLevelGreaterThan(int minLevel);
}
