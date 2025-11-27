package com.nutrix.backend.repository;

import com.nutrix.backend.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    // 1. Find local foods by region (e.g., "Give me Rift Valley foods")
    List<FoodItem> findByRegion(String region);

    // 2. Find specific nutrients (e.g., "Give me IRON rich foods")
    List<FoodItem> findByNutrientFocus(String nutrientFocus);

    // 3. 💧 The Hydration Query (New)
    // "Give me only Drinks (isDrink = true) from this Region"
    List<FoodItem> findByRegionAndIsDrinkTrue(String region);

    // 4. "Give me only Solid Food (isDrink = false) from this Region"
    List<FoodItem> findByRegionAndIsDrinkFalse(String region);
}