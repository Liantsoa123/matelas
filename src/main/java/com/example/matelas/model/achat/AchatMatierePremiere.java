package com.example.matelas.model.achat;

import com.example.matelas.model.matierepremier.MatierePremier;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class AchatMatierePremiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "matiere_premier_id")
    private MatierePremier matierePremier;
    private double quantite;
    private Date dateAchat;
    private double prixRevient ;

    public AchatMatierePremiere() {
    }

    public AchatMatierePremiere(int id, MatierePremier matierePremier, double quantite, Date dateAchat, double prixRevient) {
        this.id = id;
        this.matierePremier = matierePremier;
        this.quantite = quantite;
        this.dateAchat = dateAchat;
        this.prixRevient = prixRevient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MatierePremier getMatierePremier() {
        return matierePremier;
    }

    public void setMatierePremier(MatierePremier matierePremier) {
        this.matierePremier = matierePremier;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public double getPrixRevient() {
        return prixRevient;
    }

    public void setPrixRevient(double prixRevient) {
        this.prixRevient = prixRevient;
    }
}
