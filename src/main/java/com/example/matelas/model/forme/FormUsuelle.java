package com.example.matelas.model.forme;

import com.example.matelas.model.block.Block;
import jakarta.persistence.*;

@Entity
public class FormUsuelle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double longueur;
    private double largeur;
    private double epaisseur;
    private double prixVente;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;

    public FormUsuelle() {
    }

    public FormUsuelle(int id, double longueur, double largeur, double epaisseur, Block block, double prixVente) {
        this.id = id;
        this.longueur = longueur;
        this.largeur = largeur;
        this.epaisseur = epaisseur;
        this.block = block;
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

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
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
}
