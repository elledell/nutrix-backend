package com.nutrix.backend.model;


import jakarta.persistence.*;
import lombok.Data;

@Data // Generates Getters, Setters, and toString automatically
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private double weight; // in kg
    private double height; // in cm
    private String gender; // "MALE" or "FEMALE"

    // The Core "Bio" Features
    private String metabolicType; // "FAST", "SLOW", or "NORMAL" (Calculated by System)
    private String location; // e.g., "Eldoret", "Nairobi"

    // The "Watch" Data
    private double currentActivityFactor; // Starts at 1.2, updates via Watch Sync
}