package com.nutrix.backend.controller;

import com.nutrix.backend.dto.DailyPlanResponse;
import com.nutrix.backend.dto.HydrationResponse;
import com.nutrix.backend.model.FoodItem;
import com.nutrix.backend.repository.FoodItemRepository;
import com.nutrix.backend.service.HydrationService;
import com.nutrix.backend.service.MealGeneratorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = "*") // Allows React to talk to Spring Boot
public class MealPlanController {

    private final FoodItemRepository foodRepository;
    private final HydrationService hydrationService;
    private final MealGeneratorService mealGeneratorService; // <--- The New Service

    // Constructor Injection for all 3 dependencies
    public MealPlanController(FoodItemRepository foodRepository,
                              HydrationService hydrationService,
                              MealGeneratorService mealGeneratorService) {
        this.foodRepository = foodRepository;
        this.hydrationService = hydrationService;
        this.mealGeneratorService = mealGeneratorService;
    }

    // ---------------------------------------------------------
    // 1. NEW ENDPOINT: Generate the Full Day Plan (For React UI)
    // URL: GET /api/meals/generate?region=RIFT_VALLEY&calories=2400
    // ---------------------------------------------------------
    @GetMapping("/generate")
    public DailyPlanResponse generatePlan(@RequestParam String region, @RequestParam int calories) {
        return mealGeneratorService.generateDailyPlan(region, calories);
    }

    // ---------------------------------------------------------
    // 2. OLD ENDPOINT: Get List of Solid Foods (For debugging)
    // URL: GET /api/meals/food?region=RIFT_VALLEY
    // ---------------------------------------------------------
    @GetMapping("/food")
    public List<FoodItem> getFoodRecommendations(@RequestParam String region) {
        return foodRepository.findByRegionAndIsDrinkFalse(region);
    }

    // ---------------------------------------------------------
    // 3. OLD ENDPOINT: Get Specific Hydration Plan
    // URL: GET /api/meals/hydration?location=Eldoret&temp=26.5
    // ---------------------------------------------------------
    @GetMapping("/hydration")
    public HydrationResponse getHydrationPlan(@RequestParam String location, @RequestParam double temp) {
        return hydrationService.generatePlan(location, temp);
    }
}