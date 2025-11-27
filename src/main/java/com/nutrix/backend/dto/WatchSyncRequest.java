package com.nutrix.backend.dto;

import lombok.Data;

@Data
public class WatchSyncRequest {
    private int steps;      // e.g., 15000
    private int heartRate;  // e.g., 160
    private int caloriesBurned; // e.g., 800
    private int workoutDurationMinutes; // e.g., 90
}