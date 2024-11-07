package com.example.matelas.model.forme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FormUsuelleService {

    private final FormUsuelleRepository formUsuelleRepository;

    @Autowired
    public FormUsuelleService(FormUsuelleRepository formUsuelleRepository) {
        this.formUsuelleRepository = formUsuelleRepository;
    }

    // Get all FormUsuelle
    public List<FormUsuelle> getAllFormUsuelles() {
        return formUsuelleRepository.findAll();
    }

    // Get a FormUsuelle by ID
    public Optional<FormUsuelle> getFormUsuelleById(int id) {
        return formUsuelleRepository.findById(id);
    }

    // Save a new FormUsuelle
    public FormUsuelle saveFormUsuelle(FormUsuelle formUsuelle) {
        return formUsuelleRepository.save(formUsuelle);
    }

    // Update an existing FormUsuelle
    public FormUsuelle updateFormUsuelle(int id, FormUsuelle formUsuelleDetails) {
        return formUsuelleRepository.findById(id).map(formUsuelle -> {
            formUsuelle.setLongueur(formUsuelleDetails.getLongueur());
            formUsuelle.setLargeur(formUsuelleDetails.getLargeur());
            formUsuelle.setEpaisseur(formUsuelleDetails.getEpaisseur());
            formUsuelle.setPrixVente(formUsuelleDetails.getPrixVente());
            return formUsuelleRepository.save(formUsuelle);
        }).orElseThrow(() -> new RuntimeException("FormUsuelle not found with id " + id));
    }

    // Delete a FormUsuelle by ID
    public void deleteFormUsuelle(int id) {
        if (formUsuelleRepository.existsById(id)) {
            formUsuelleRepository.deleteById(id);
        } else {
            throw new RuntimeException("FormUsuelle not found with id " + id);
        }
    }

    public  FormUsuelle bestRationVenteVolumn (){
            List<FormUsuelle> formUsuelles = formUsuelleRepository.findAll();
            return formUsuelles.stream()
                    .max(Comparator.comparing(FormUsuelle::rationVenteVolum))
                    .orElseThrow(() -> new IllegalStateException("No FormUsuelle found"));
    }
    public Optional<FormUsuelle> getFormUsuelleWithSmallestVolume() {
        return formUsuelleRepository.findTopBySmallestVolume();
    }
}
