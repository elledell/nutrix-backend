package com.nutrix.backend.repository;

import com.nutrix.backend.model.BannedSubstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannedSubstanceRepository extends JpaRepository<BannedSubstance, String> {

    // The "Search Engine" logic
    // If user types "fedrine", it finds "Pseudoephedrine"
    List<BannedSubstance> findByNameContainingIgnoreCase(String name);
}