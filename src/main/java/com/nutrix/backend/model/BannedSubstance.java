package com.nutrix.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BannedSubstance {

    @Id
    private String name; // e.g., "Pseudoephedrine" (The Ingredient, not just the brand)

    private String category; // e.g., "Stimulant", "Steroid", "Diuretic"
    private String riskLevel; // "BANNED", "RESTRICTED", "SAFE"
    private String warningMessage; // What the AI tells the athlete
}