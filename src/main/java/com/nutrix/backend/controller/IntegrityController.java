package com.nutrix.backend.controller;

import com.nutrix.backend.service.IntegrityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/integrity")
@CrossOrigin(origins = "*")
public class IntegrityController {

    private final IntegrityService integrityService;

    public IntegrityController(IntegrityService integrityService) {
        this.integrityService = integrityService;
    }

    // 1. Check Medicine
    // URL: GET /api/integrity/check?name=Panadol
    @GetMapping("/check")
    public String checkMedicine(@RequestParam String name) {
        return integrityService.checkSafety(name);
    }
}