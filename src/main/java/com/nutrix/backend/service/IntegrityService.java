package com.nutrix.backend.service;

import com.nutrix.backend.model.BannedSubstance;
import com.nutrix.backend.repository.BannedSubstanceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IntegrityService {

    private final BannedSubstanceRepository repository;

    public IntegrityService(BannedSubstanceRepository repository) {
        this.repository = repository;
    }

    public String checkSafety(String medicineName) {
        // 1. Search DB (Ignore case: "panadol" finds "Panadol")
        List<BannedSubstance> risks = repository.findByNameContainingIgnoreCase(medicineName);

        if (risks.isEmpty()) {
            return "✅ SAFE: No banned ingredients found in '" + medicineName + "'.";
        }

        // 2. If found, return the warning
        BannedSubstance badItem = risks.get(0);
        return "⚠️ WARNING: " + badItem.getName() +
                " is categorized as " + badItem.getRiskLevel() +
                ". " + badItem.getWarningMessage();
    }
}