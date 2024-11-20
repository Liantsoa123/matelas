package com.example.matelas.model.matierepremier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatierePremierService {

    private final MatierePremierRepository matierePremierRepository;

    @Autowired
    public MatierePremierService(MatierePremierRepository matierePremierRepository) {
        this.matierePremierRepository = matierePremierRepository;
    }

    // Get all MatierePremier
    public List<MatierePremier> getAllMatierePremiers() {
        return matierePremierRepository.findAll();
    }

    // Get MatierePremier by ID
    public MatierePremier getMatierePremierById(int id) {
        Optional<MatierePremier> matierePremier = matierePremierRepository.findById(id);
        return matierePremier.orElseThrow(() -> new RuntimeException("MatierePremier not found with id: " + id));
    }

    // Save a new MatierePremier
    public MatierePremier saveMatierePremier(MatierePremier matierePremier) {
        return matierePremierRepository.save(matierePremier);
    }

    // Update an existing MatierePremier
    public MatierePremier updateMatierePremier(int id, MatierePremier updatedMatierePremier) {
        MatierePremier existingMatierePremier = getMatierePremierById(id);
        existingMatierePremier.setNom(updatedMatierePremier.getNom());
        existingMatierePremier.setUnite(updatedMatierePremier.getUnite());
        return matierePremierRepository.save(existingMatierePremier);
    }

    // Delete a MatierePremier by ID
    public void deleteMatierePremier(int id) {
        if (!matierePremierRepository.existsById(id)) {
            throw new RuntimeException("MatierePremier not found with id: " + id);
        }
        matierePremierRepository.deleteById(id);
    }
}
