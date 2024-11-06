package com.example.matelas.model.forme;

import com.example.matelas.model.block.Block;
import jakarta.persistence.*;

@Entity
public class FormUsuelle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String name ;
    private double longueur;
    private double largeur;
    private double epaisseur;
    private double prixVente;
     @Transient
     private int quantiteTransformation = 0;


    public FormUsuelle() {
    }

    public FormUsuelle(int id, String name, double longueur, double largeur, double epaisseur, double prixVente) {
        this.id = id;
        this.name = name;
        this.longueur = longueur;
        this.largeur = largeur;
        this.epaisseur = epaisseur;

        this.prixVente = prixVente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getEpaisseur() {
        return epaisseur;
    }

    public void setEpaisseur(double epaisseur) {
        this.epaisseur = epaisseur;
    }


    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public double volume() {
        return this.getLongueur() * this.getLargeur() * this.getEpaisseur();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantiteTransformation() {
        return quantiteTransformation;
    }

    public void setQuantiteTransformation(int quantiteTransformation) {
        this.quantiteTransformation = quantiteTransformation;
    }
}
