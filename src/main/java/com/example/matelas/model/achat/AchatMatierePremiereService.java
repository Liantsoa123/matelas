package com.example.matelas.model.achat;


import com.example.matelas.model.achat.AchatMatierePremiere;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AchatMatierePremiereService {

    @Autowired
    private AchatMatierePremiereRepository achatMatierePremiereRepository;
    private final EntityManager entityManager;

    public AchatMatierePremiereService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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

    @Transactional
    public void importCsv (String query){
        entityManager.createNativeQuery(query).executeUpdate();
        System.out.println("Insertion finished");
    }

    public AchatMatierePremiere getByIdMatiereAndDate(int idmatiere, Date date, List<AchatMatierePremiere> achatMatierePremiereList) {
        for (AchatMatierePremiere achatMatierePremiere : achatMatierePremiereList) {
            // Check if matierePremier and dateAchat are not null
            if (achatMatierePremiere.getMatierePremier() != null &&
                    achatMatierePremiere.getMatierePremier().getId() == idmatiere &&
                    achatMatierePremiere.getDateAchat() != null &&
                    achatMatierePremiere.getDateAchat().compareTo(date) <= 0 && // Use compareTo for Date comparison
                    achatMatierePremiere.getQuantite() > 0) {
                return achatMatierePremiere;
            }
        }
        return null;
    }

    public  List<AchatMatierePremiere> getAllAchatMatierePremieresAsc(){
        return achatMatierePremiereRepository.getAllAchatMatierePremieres();
    }


    public List<AchatMatierePremiere> saveAllAchatMatierePremieres(List<AchatMatierePremiere> achatMatierePremiereList) {
        if (achatMatierePremiereList == null || achatMatierePremiereList.isEmpty()) {
            throw new IllegalArgumentException("The list of AchatMatierePremiere to save cannot be null or empty.");
        }

        return achatMatierePremiereRepository.saveAll(achatMatierePremiereList);
    }


}