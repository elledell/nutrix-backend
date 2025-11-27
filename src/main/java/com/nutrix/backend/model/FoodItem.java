package com.nutrix.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "food_items")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // e.g., "Madafu"
    private String localName;   // e.g., "Coconut Water"

    // The "Hyper-Local" Logic
    private String region;      // "RIFT_VALLEY", "COASTAL", "WESTERN", "ALL"

    // The "Nutrition" Logic
    private String nutrientFocus; // "RECOVERY", "ENERGY", "ELECTROLYTES"
    private String benefit;       // e.g., "Replaces potassium lost in sweat"
    private boolean isIndigenous; // true = Local Superfood

    // 💧 The Hydration Property
    private boolean isDrink;      // true for Mursik/Soup, false for Ugali
}