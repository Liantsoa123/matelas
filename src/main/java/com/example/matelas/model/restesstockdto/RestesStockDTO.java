package com.example.matelas.model.restesstockdto;

import com.example.matelas.model.achat.AchatMatierePremiere;

import java.sql.Date;

public class RestesStockDTO {
    private int id;
    private AchatMatierePremiere achatMatierePremiere; // Utilisation de l'objet AchatMatierePremiere
    private double quantiteReste;
    private Date dateAchatMatierePremiere;

    public RestesStockDTO(int id, AchatMatierePremiere achatMatierePremiere, double quantiteReste, Date dateAchatMatierePremiere) {
        this.id = id;
        this.achatMatierePremiere = achatMatierePremiere;
        this.quantiteReste = quantiteReste;
        this.dateAchatMatierePremiere = dateAchatMatierePremiere;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AchatMatierePremiere getAchatMatierePremiere() {
        return achatMatierePremiere;
    }

    public void setAchatMatierePremiere(AchatMatierePremiere achatMatierePremiere) {
        this.achatMatierePremiere = achatMatierePremiere;
    }

    public double getQuantiteReste() {
        return quantiteReste;
    }

    public void setQuantiteReste(double quantiteReste) {
        this.quantiteReste = quantiteReste;
    }

    public Date getDateAchatMatierePremiere() {
        return dateAchatMatierePremiere;
    }

    public void setDateAchatMatierePremiere(Date dateAchatMatierePremiere) {
        this.dateAchatMatierePremiere = dateAchatMatierePremiere;
    }
}
