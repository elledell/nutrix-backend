package com.nutrix.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class DailyPlanResponse {
    private String date; // "Today's Fuel"
    private int totalCalories; // e.g., 2400
    private MealSlot morning;
    private MealSlot afternoon;
    private MealSlot evening;
    private MealSlot snack;
}