package com.example.matelas.model.block;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double longueur;
    private double largeur;
    private double epaisseur;
    private double prixRevient;
    private Date creationBlock;
    @ManyToOne
    private Block mere;

    public Block(double longueur, double largeur, double epaisseur, double prixRevient, Date creationBlock) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.epaisseur = epaisseur;
        this.prixRevient = prixRevient;
        this.creationBlock = creationBlock;
        this.mere = null;
        this.id = 0 ;
    }

    public Block() {
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

    public double getPrixRevient() {
        return prixRevient;
    }

    public void setPrixRevient(double prixRevient) {
        this.prixRevient = prixRevient;
    }

    public Block getMere() {
        return mere;
    }

    public void setMere(Block mere) {
        this.mere = mere;
    }

    public Date getCreationBlock() {
        return creationBlock;
    }

    public void setCreationBlock(Date creationBlock) {
        this.creationBlock = creationBlock;
    }

    public double volume() {
        return this.getLongueur() * this.getLargeur() * this.getEpaisseur();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public  String getName (){
        return "L="+this.getLongueur()+"/l="+this.getLargeur()+"/e="+getEpaisseur()+"/V="+volume();

    }
}
