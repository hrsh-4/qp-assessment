package com.grocery.service;

import com.grocery.model.GroceryItem;
import com.grocery.repository.GroceryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroceryItemService {
    private final GroceryItemRepository groceryItemRepository;

    @Transactional
    public GroceryItem addGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    public GroceryItem getGroceryItemById(Long id) {
        return groceryItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grocery item not found with id: " + id));
    }

    @Transactional
    public GroceryItem updateGroceryItem(Long id, GroceryItem updatedItem) {
        GroceryItem existingItem = getGroceryItemById(id);
        existingItem.setName(updatedItem.getName());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setInventoryLevel(updatedItem.getInventoryLevel());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setDescription(updatedItem.getDescription());
        return groceryItemRepository.save(existingItem);
    }

    @Transactional
    public void deleteGroceryItem(Long id) {
        GroceryItem item = getGroceryItemById(id);
        groceryItemRepository.delete(item);
    }

    @Transactional
    public void updateInventoryLevel(Long id, int quantity) {
        GroceryItem item = getGroceryItemById(id);
        int newInventoryLevel = item.getInventoryLevel() + quantity;
        if (newInventoryLevel < 0) {
            throw new IllegalArgumentException("Inventory cannot be negative");
        }
        item.setInventoryLevel(newInventoryLevel);
        groceryItemRepository.save(item);
    }

    public List<GroceryItem> getAvailableGroceryItems() {
        return groceryItemRepository.findByInventoryLevelGreaterThan(0);
    }
}
