package com.grocery.controller;

import com.grocery.model.GroceryItem;
import com.grocery.service.GroceryItemService;
// import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grocery-items")
@RequiredArgsConstructor
public class GroceryItemController {
    private final GroceryItemService groceryItemService;

    @PostMapping
    public ResponseEntity<GroceryItem> addGroceryItem(@RequestBody GroceryItem groceryItem) {
        GroceryItem savedItem = groceryItemService.addGroceryItem(groceryItem);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GroceryItem>> getAllGroceryItems() {
        List<GroceryItem> items = groceryItemService.getAllGroceryItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/available")
    public ResponseEntity<List<GroceryItem>> getAvailableGroceryItems() {
        List<GroceryItem> availableItems = groceryItemService.getAvailableGroceryItems();
        return ResponseEntity.ok(availableItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable Long id) {
        GroceryItem item = groceryItemService.getGroceryItemById(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(
            @PathVariable Long id,
         @RequestBody GroceryItem groceryItem
    ) {
        GroceryItem updatedItem = groceryItemService.updateGroceryItem(id, groceryItem);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable Long id) {
        groceryItemService.deleteGroceryItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inventory")
    public ResponseEntity<Void> updateInventoryLevel(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        groceryItemService.updateInventoryLevel(id, quantity);
        return ResponseEntity.ok().build();
    }
}
