package com.nutrix.backend.service;

import com.nutrix.backend.model.User;
import com.nutrix.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PhysiologyService {

    private final UserRepository userRepository;
    private final ActivityService activityService;

    public PhysiologyService(UserRepository userRepository, ActivityService activityService) {
        this.userRepository = userRepository;
        this.activityService = activityService;
    }

    // A. CALCULATE CALORIES (The Math)
    public double calculateDailyCalories(User user) {
        double bmr;

        // 1. Mifflin-St Jeor Equation (Gold Standard)
        if ("MALE".equalsIgnoreCase(user.getGender())) {
            bmr = (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * user.getAge()) + 5;
        } else {
            bmr = (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * user.getAge()) - 161;
        }

        // 2. Apply Genetic Logic (If known)
        if ("FAST".equalsIgnoreCase(user.getMetabolicType())) {
            bmr *= 1.15; // Fast engine burns 15% more
        } else if ("SLOW".equalsIgnoreCase(user.getMetabolicType())) {
            bmr *= 0.90; // Efficient engine burns 10% less
        }

        // 3. Multiply by Activity Factor (from the Watch)
        return bmr * user.getCurrentActivityFactor();
    }

    // B. SYNC WATCH DATA (The Action)
    public User processWatchSync(Long userId, int steps, int heartRate, int duration) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Calculate new Factor
        double newFactor = activityService.calculateRealTimeFactor(steps, duration, heartRate);
        user.setCurrentActivityFactor(newFactor);

        // 2. Auto-Detect Metabolism (The "AI" part)
        // If they burn huge energy relative to weight, they are Hyper-Metabolic
        double ratio = (user.getWeight() * 24) * newFactor;
        if (newFactor > 2.0) {
            user.setMetabolicType("FAST");
        } else {
            user.setMetabolicType("NORMAL");
        }

        return userRepository.save(user);
    }
}