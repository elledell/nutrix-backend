package com.nutrix.backend.service;

import com.nutrix.backend.dto.DailyPlanResponse;
import com.nutrix.backend.dto.MealSlot;
import com.nutrix.backend.model.FoodItem;
import com.nutrix.backend.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MealGeneratorService {

    private final FoodItemRepository foodRepository;

    public MealGeneratorService(FoodItemRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public DailyPlanResponse generateDailyPlan(String region, int targetCalories) {
        DailyPlanResponse plan = new DailyPlanResponse();
        plan.setDate("Today's Fuel");
        plan.setTotalCalories(targetCalories);

        // 1. Fetch All Foods for the Region (e.g., RIFT_VALLEY + ALL)
        List<FoodItem> regionalFoods = foodRepository.findByRegion(region);
        regionalFoods.addAll(foodRepository.findByRegion("ALL")); // Add universal foods like Eggs/Ugali

        // 2. Categorize them (The "Balance" Logic)
        List<FoodItem> carbs = filterBy(regionalFoods, List.of("CARBS", "STARCH", "GLYCOGEN", "ENERGY", "COMPLEX_CARBS"));
        List<FoodItem> proteins = filterBy(regionalFoods, List.of("PROTEIN", "PROBIOTICS"));
        List<FoodItem> vitamins = filterBy(regionalFoods, List.of("VITAMINS", "IRON_FIBER", "BETA_CAROTENE", "CALCIUM"));
        List<FoodItem> drinks = regionalFoods.stream().filter(FoodItem::isDrink).collect(Collectors.toList());

        // 3. Shuffle to ensure "No Repetition" every time we call this
        Collections.shuffle(carbs);
        Collections.shuffle(proteins);
        Collections.shuffle(vitamins);
        Collections.shuffle(drinks);

        // 4. Build Slots (Pop off items so they aren't reused)
        plan.setMorning(createBreakfast(carbs, proteins, drinks));
        plan.setAfternoon(createBalancedMeal("Lunch", "13:00 PM", 800, carbs, proteins, vitamins, drinks));
        plan.setEvening(createBalancedMeal("Dinner", "19:00 PM", 600, carbs, proteins, vitamins, drinks));

        // Snack (Leftover fruit or nuts)
        plan.setSnack(createSnack(regionalFoods));

        return plan;
    }

    // --- Helper Logic ---

    private MealSlot createBreakfast(List<FoodItem> carbs, List<FoodItem> proteins, List<FoodItem> drinks) {
        MealSlot slot = new MealSlot();
        slot.setTime("07:30 AM");
        slot.setCategory("Breakfast");

        // Pick 1 light carb (e.g., Uji or Sweet Potato)
        FoodItem carb = getNext(carbs);
        // Pick 1 protein (e.g., Eggs)
        FoodItem protein = getNext(proteins);

        slot.setMainDish(carb != null ? carb.getName() : "Millet Porridge");
        slot.setStew(protein != null ? "Boiled " + protein.getName() : "Boiled Eggs");
        slot.setDrink(getNext(drinks) != null ? getNext(drinks).getName() : "Tea");
        slot.setCalories(500);
        slot.setTags(List.of("Energy Start", "Light"));
        return slot;
    }

    private MealSlot createBalancedMeal(String cat, String time, int cals, List<FoodItem> carbs, List<FoodItem> proteins, List<FoodItem> vitamins, List<FoodItem> drinks) {
        MealSlot slot = new MealSlot();
        slot.setTime(time);
        slot.setCategory(cat);

        // The "Balanced Plate" Rule: 1 Carb + 1 Protein + 1 Veg
        FoodItem carb = getNext(carbs);
        FoodItem protein = getNext(proteins);
        FoodItem veg = getNext(vitamins); // Managu, Saga, etc.
        FoodItem drink = getNext(drinks);

        slot.setMainDish(carb != null ? carb.getName() : "Ugali");
        // Combine Protein + Veg into a "Stew" string for the UI
        String stewName = (protein != null ? protein.getName() : "Beef") + " with " + (veg != null ? veg.getName() : "Greens");
        slot.setStew(stewName);

        slot.setDrink(drink != null ? drink.getName() : "Water");
        slot.setCalories(cals);
        slot.setTags(List.of("Balanced", "Recovery"));

        return slot;
    }

    private MealSlot createSnack(List<FoodItem> allFoods) {
        MealSlot slot = new MealSlot();
        slot.setTime("16:00 PM");
        slot.setCategory("Snack");
        // Find a fruit or nut
        FoodItem snack = allFoods.stream()
                .filter(f -> f.getNutrientFocus().contains("POTASSIUM") || f.getNutrientFocus().contains("ENERGY"))
                .findFirst().orElse(null);

        slot.setMainDish(snack != null ? snack.getName() : "Banana");
        slot.setStew("N/A");
        slot.setCalories(200);
        return slot;
    }

    // Helper to filter foods by nutrient keywords
    private List<FoodItem> filterBy(List<FoodItem> all, List<String> keywords) {
        return all.stream()
                .filter(f -> keywords.stream().anyMatch(k -> f.getNutrientFocus().contains(k)))
                .collect(Collectors.toList());
    }

    // Helper to pop the next item from list safely (Prevents repeats!)
    private FoodItem getNext(List<FoodItem> list) {
        if (list.isEmpty()) return null;
        return list.remove(0); // Returns item and Removes it so it can't be picked again
    }
}