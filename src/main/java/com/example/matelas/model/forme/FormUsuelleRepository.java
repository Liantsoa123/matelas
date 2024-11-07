package com.example.matelas.model.forme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormUsuelleRepository extends JpaRepository<FormUsuelle, Integer> {
    @Query("SELECT f FROM FormUsuelle f ORDER BY (f.longueur * f.largeur * f.epaisseur) ASC  limit  1 ")
    Optional<FormUsuelle> findTopBySmallestVolume();
}
