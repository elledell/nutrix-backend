package com.nutrix.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class MealSlot {
    private String time;        // e.g., "08:00 AM"
    private String category;    // "Breakfast" or "Lunch"
    private String mainDish;    // e.g., "Ugali"
    private String stew;        // e.g., "Beef Stew" or "Managu"
    private String drink;       // e.g., "Mursik"
    private int calories;       // e.g., 600
    private List<String> tags;  // ["High Carb", "Recovery"]
}