package com.example.matelas.model.achat;


import com.example.matelas.model.achat.AchatMatierePremiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchatMatierePremiereService {

    @Autowired
    private AchatMatierePremiereRepository achatMatierePremiereRepository;

    // Get all AchatMatierePremiere records
    public List<AchatMatierePremiere> getAllAchatMatierePremieres() {
        return achatMatierePremiereRepository.findAll();
    }

    // Get a single AchatMatierePremiere by ID
    public Optional<AchatMatierePremiere> getAchatMatierePremiereById(int id) {
        return achatMatierePremiereRepository.findById(id);
    }

    // Save or update an AchatMatierePremiere
    public AchatMatierePremiere saveAchatMatierePremiere(AchatMatierePremiere achatMatierePremiere) {
        return achatMatierePremiereRepository.save(achatMatierePremiere);
    }

    // Delete an AchatMatierePremiere by ID
    public void deleteAchatMatierePremiere(int id) {
        achatMatierePremiereRepository.deleteById(id);
    }
}
