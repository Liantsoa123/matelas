package com.example.matelas.model.formule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormuleService {

    @Autowired
    private FormuleRepository formuleRepository;

    // Retrieve all Formules
    public List<Formule> getAllFormules() {
        return formuleRepository.findAll();
    }

    // Retrieve a Formule by ID
    public Formule getFormuleById(int id) {
        Optional<Formule> optionalFormule = formuleRepository.findById(id);
        if (optionalFormule.isPresent()) {
            return optionalFormule.get();
        } else {
            throw new RuntimeException("Formule not found for ID: " + id);
        }
    }

    // Save or update a Formule
    public Formule saveFormule(Formule formule) {
        return formuleRepository.save(formule);
    }

    // Delete a Formule by ID
    public void deleteFormule(int id) {
        if (formuleRepository.existsById(id)) {
            formuleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Formule not found for ID: " + id);
        }
    }
}
