package com.example.matelas.model.matierepremier;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MatierePremier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reference;
    private String nom;
    private String unite ;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public MatierePremier() {
    }

    public MatierePremier(String reference, String nom, String unite) {
        this.reference = reference;
        this.nom = nom;
        this.unite = unite;
    }

}
