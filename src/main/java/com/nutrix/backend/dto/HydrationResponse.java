package com.nutrix.backend.dto;

import lombok.Data;

@Data // <--- CRITICAL: This generates the 'setTargetLiters', 'getTargetLiters', etc.
public class HydrationResponse {

    private double targetLiters;           // e.g., 3.5
    private String weatherWarning;         // e.g., "High Heat Alert (28°C)"
    private boolean electrolyteRecommendation; // true if sweating heavily
    private String localDrinkSuggestion;   // e.g., "Madafu (Coconut Water)"
}