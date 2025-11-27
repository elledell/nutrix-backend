package com.nutrix.backend.service;
import com.nutrix.backend.dto.HydrationResponse;
import com.nutrix.backend.model.FoodItem;
import com.nutrix.backend.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class HydrationService {

    private final FoodItemRepository foodRepository;

    public HydrationService(FoodItemRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public HydrationResponse generatePlan(String location, double temp) {
        HydrationResponse response = new HydrationResponse();

        // 1. Calculate Water Needs based on Heat
        double baseLiters = 2.5;
        if (temp > 25.0) baseLiters += 1.0; // Hot day
        if (temp > 30.0) baseLiters += 0.5; // Very hot

        // FIXED: Make sure your HydrationResponse DTO has these fields and @Data annotation!
        response.setTargetLiters(baseLiters);
        response.setWeatherWarning(temp > 25 ? "High Heat Alert (" + temp + "°C)" : "Conditions Normal");

        // 2. Recommend Local Drink based on Location
        String region = determineRegion(location);

        // FIXED: Use the new specific method we added to the Repository
        List<FoodItem> localDrinks = foodRepository.findByRegionAndIsDrinkTrue(region);

        if (localDrinks.isEmpty()) {
            response.setLocalDrinkSuggestion("Water & Pinch of Salt (Standard)");
        } else {
            // Pick a random local drink
            FoodItem drink = localDrinks.get(new Random().nextInt(localDrinks.size()));
            response.setLocalDrinkSuggestion(drink.getName() + " (" + drink.getBenefit() + ")");
        }

        response.setElectrolyteRecommendation(temp > 25);

        return response;
    }

    private String determineRegion(String city) {
        if (city == null) return "ALL"; // Safety check
        if (city.equalsIgnoreCase("ELDORET") || city.equalsIgnoreCase("ITEN")) return "RIFT_VALLEY";
        if (city.equalsIgnoreCase("MOMBASA") || city.equalsIgnoreCase("KILIFI")) return "COASTAL";
        if (city.equalsIgnoreCase("KISUMU")) return "WESTERN";
        return "ALL";
    }
}