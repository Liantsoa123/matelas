package com.example.matelas.model.matierepremier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatierePremierService {

    @Autowired
    private MatierePremierRepository matierePremierRepository;

    // Get all MatierePremiers
    public List<MatierePremier> getAllMatierePremiers() {
        return matierePremierRepository.findAll();
    }

    // Get a single MatierePremier by reference
    public Optional<MatierePremier> getMatierePremierByReference(String reference) {
        return matierePremierRepository.findById(reference);
    }

    // Save or update a MatierePremier
    public MatierePremier saveMatierePremier(MatierePremier matierePremier) {
        return matierePremierRepository.save(matierePremier);
    }

    // Delete a MatierePremier by reference
    public void deleteMatierePremier(String reference) {
        matierePremierRepository.deleteById(reference);
    }
}
