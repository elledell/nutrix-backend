package com.nutrix.backend.service;

import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    // Logic: Convert Watch Data -> Activity Factor (1.2 to 2.4)
    public double calculateRealTimeFactor(int steps, int workoutMinutes, int avgHeartRate) {

        // 1. Start at Coma/Sleep Level
        double factor = 1.2;

        // 2. Steps Bonus: Every 5,000 steps adds +0.1
        // Example: 15,000 steps = +0.3
        if (steps > 0) {
            factor += (steps / 5000.0) * 0.1;
        }

        // 3. Training Duration Bonus: Every 30 mins adds +0.15
        if (workoutMinutes > 0) {
            factor += (workoutMinutes / 30.0) * 0.15;
        }

        // 4. Intensity Bonus (The Heart Rate Kicker)
        // If HR > 150 (Threshold/Tempo run), add massive bonus
        if (avgHeartRate > 150) {
            factor += 0.35;
        } else if (avgHeartRate > 120) {
            factor += 0.15;
        }

        // 5. Safety Cap (Human Limit is around 2.4-2.5)
        if (factor > 2.5) return 2.5;

        return Math.round(factor * 100.0) / 100.0; // Round to 2 decimal places
    }
}