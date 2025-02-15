package com.grocery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.context.annotation.Bean;
import okhttp3.OkHttpClient;

@FeignClient(name = "grocery-service", configuration = GroceryItemClient.FeignConfig.class)
public interface GroceryItemClient {
    @GetMapping("/api/grocery-items/{id}")
    Object getGroceryItem(@PathVariable("id") Long id);

    @PatchMapping("/api/grocery-items/{id}/inventory")
    void updateInventoryLevel(
        @PathVariable("id") Long id, 
        @RequestParam("quantity") int quantity
    );

    class FeignConfig {
        @Bean
        public OkHttpClient client() {
            return new OkHttpClient.Builder()
                    .build();
        }
    }
}
