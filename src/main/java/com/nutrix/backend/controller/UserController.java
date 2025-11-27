package com.nutrix.backend.controller;

import com.nutrix.backend.dto.WatchSyncRequest;
import com.nutrix.backend.model.User;
import com.nutrix.backend.repository.UserRepository;
import com.nutrix.backend.service.PhysiologyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*") // Allows React to talk to Spring Boot
public class UserController {

    private final UserRepository userRepository;
    private final PhysiologyService physiologyService;

    public UserController(UserRepository userRepository, PhysiologyService physiologyService) {
        this.userRepository = userRepository;
        this.physiologyService = physiologyService;
    }

    // 1. Create a User (Profile Setup)
    // URL: POST /api/user
    @PostMapping
    public User createUser(@RequestBody User user) {
        // Set defaults
        if (user.getCurrentActivityFactor() == 0) user.setCurrentActivityFactor(1.2);
        if (user.getMetabolicType() == null) user.setMetabolicType("NORMAL");
        return userRepository.save(user);
    }

    // 2. Get User Details
    // URL: GET /api/user/1
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 3. The "Watch Sync" Feature (Simulated Bluetooth)
    // URL: POST /api/user/1/sync
    @PostMapping("/{id}/sync")
    public User syncWatch(@PathVariable Long id, @RequestBody WatchSyncRequest request) {
        // Calls the Physiology Service to calculate the new Activity Factor
        return physiologyService.processWatchSync(id, request.getSteps(), request.getHeartRate(), request.getWorkoutDurationMinutes());
    }
}