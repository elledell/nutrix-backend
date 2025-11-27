package com.nutrix.backend.config;

import com.nutrix.backend.model.BannedSubstance;
import com.nutrix.backend.model.FoodItem;
import com.nutrix.backend.repository.BannedSubstanceRepository;
import com.nutrix.backend.repository.FoodItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final FoodItemRepository foodRepository;
    private final BannedSubstanceRepository bannedRepository;

    public DataSeeder(FoodItemRepository foodRepository, BannedSubstanceRepository bannedRepository) {
        this.foodRepository = foodRepository;
        this.bannedRepository = bannedRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedFoods();
        seedBannedSubstances();
    }

    private void seedFoods() {
        if (foodRepository.count() > 0) return;

        List<FoodItem> foods = List.of(
                // --- RIFT VALLEY & NANDI SPECIALTIES ---
                createFood("African Nightshade", "Managu / Osuga", "RIFT_VALLEY", "IRON_FIBER", "Restores red blood cells after high-altitude runs", true, false),
                createFood("Spider Plant", "Saga / Saget", "RIFT_VALLEY", "BETA_CAROTENE", "Reduces inflammation after heavy training", true, false),
                createFood("Fermented Milk", "Mursik", "RIFT_VALLEY", "PROBIOTICS", "Gut health & hydration", true, true),
                createFood("Boiled Milk", "Maziwa Chemsha", "RIFT_VALLEY", "CALCIUM", "Bone repair and strengthening", false, true),

                // --- WESTERN & NYANZA SPECIALTIES ---
                createFood("Tilapia", "Samaki", "WESTERN", "PROTEIN", "Muscle repair (Post-workout growth)", false, false),
                createFood("Sorghum", "Mtama", "WESTERN", "MAGNESIUM", "Prevents muscle cramping", true, false),
                createFood("Arrow Roots", "Nduma", "WESTERN", "STARCH", "Slow energy depletion (High stamina)", true, false),
                createFood("Groundnuts", "Njugu Karanga", "WESTERN", "ENERGY", "Easily metabolized for energy giving", true, false),
                createFood("Raw Bananas", "Ndizi", "NYANZA", "POTASSIUM", "Boosts immunity and restores electrolyte balance", true, false),

                // --- CENTRAL & EASTERN ---
                createFood("Amaranth Greens", "Terere", "CENTRAL", "CALCIUM", "Prevents stress fractures (Bone Health)", true, false),
                createFood("Sweet Potato", "Ngwaci", "CENTRAL", "GLYCOGEN", "Pre-Workout Fuel", true, false),

                // --- ALL KENYA (Universal Foods) ---
                createFood("Finger Millet Porridge", "Uji wa Wimbi", "ALL", "COMPLEX_CARBS", "Slow-release energy for long runs (Endurance)", true, true),
                createFood("Eggs", "Mayai", "ALL", "PROTEIN", "Endurance and post-workout healing", false, false),
                createFood("Kales", "Skuma Wiki", "ALL", "VITAMINS", "Boosts immunity (High Iron)", false, false),
                createFood("Beef", "Nyama ya Ngombe", "ALL", "PROTEIN", "Muscle growth and body repair", false, false),
                createFood("Goat Meat", "Nyama ya Mbuzi", "ALL", "PROTEIN", "Muscle growth and body repair", false, false),
                createFood("Avocado", "Parachichi", "ALL", "VITAMINS", "Systemic antifungal properties & Immunity", true, false)
        );

        foodRepository.saveAll(foods);
        System.out.println("✅ Nutrix Database: Seeded " + foods.size() + " Kenyan Foods.");
    }

    private void seedBannedSubstances() {
        if (bannedRepository.count() > 0) return;

        List<BannedSubstance> substances = List.of(
                createBanned("Cold Cap / Sinofed", "Pseudoephedrine", "BANNED (In-Competition)", "⚠️ STOP: Contains Pseudoephedrine. Banned during races. Do not take 24hrs before competition."),
                createBanned("Ventolin Inhaler", "Salbutamol", "RESTRICTED", "⚠️ CAUTION: Allowed ONLY if inhaled. Max 1600mcg/day. Do not swallow pills."),
                createBanned("Brufen / Nurofen", "Ibuprofen", "SAFE", "✅ SAFE: You can use this for pain relief."),
                createBanned("Lasix", "Furosemide", "BANNED (Always)", "🚫 DANGER: This is a Diuretic (Masking Agent). Strictly Banned. 4-Year Ban Risk."),
                createBanned("Prednisolone", "Glucocorticoids", "BANNED (In-Competition)", "⚠️ STOP: Steroid. Banned during competition periods."),
                createBanned("Panadol", "Paracetamol", "SAFE", "✅ SAFE: Standard painkiller. No WADA restrictions.")
        );

        bannedRepository.saveAll(substances);
        System.out.println("✅ Nutrix Database: Seeded " + substances.size() + " Medicines.");
    }

    private FoodItem createFood(String name, String localName, String region, String nutrient, String benefit, boolean isIndigenous, boolean isDrink) {
        FoodItem item = new FoodItem();
        item.setName(name);
        item.setLocalName(localName);
        item.setRegion(region);
        item.setNutrientFocus(nutrient);
        item.setBenefit(benefit);
        item.setIndigenous(isIndigenous);
        item.setDrink(isDrink);
        return item;
    }

    private BannedSubstance createBanned(String name, String ingredient, String status, String warning) {
        BannedSubstance sub = new BannedSubstance();
        sub.setName(name);
        sub.setCategory(ingredient);
        sub.setRiskLevel(status);
        sub.setWarningMessage(warning);
        return sub;
    }
}