package com.example.matelas.model.sortie;


import com.example.matelas.model.sortie.SortieMatierePremiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SortieMatierePremiereService {

    private final SortieMatierePremiereRepository sortieMatierePremiereRepository;

    @Autowired
    public SortieMatierePremiereService(SortieMatierePremiereRepository sortieMatierePremiereRepository) {
        this.sortieMatierePremiereRepository = sortieMatierePremiereRepository;
    }

    // Méthode pour obtenir toutes les sorties de matières premières
    public List<SortieMatierePremiere> getAllSorties() {
        return sortieMatierePremiereRepository.findAll();
    }

    // Méthode pour obtenir une sortie de matière première par son ID
    public Optional<SortieMatierePremiere> getSortieById(int id) {
        return sortieMatierePremiereRepository.findById(id);
    }

    // Méthode pour ajouter une nouvelle sortie de matière première
    public SortieMatierePremiere addSortie(SortieMatierePremiere sortie) {
        return sortieMatierePremiereRepository.save(sortie);
    }

    // Méthode pour mettre à jour une sortie de matière première existante
    public SortieMatierePremiere updateSortie(int id, SortieMatierePremiere sortie) {
        if (sortieMatierePremiereRepository.existsById(id)) {
            sortie.setId(id);
            return sortieMatierePremiereRepository.save(sortie);
        }
        return null;
    }

    // Méthode pour supprimer une sortie de matière première
    public void deleteSortie(int id) {
        if (sortieMatierePremiereRepository.existsById(id)) {
            sortieMatierePremiereRepository.deleteById(id);
        }
    }
}
