package com.example.matelas.model.sortie;

import com.example.matelas.model.achat.AchatMatierePremiere;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class SortieMatierePremiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateSortie;
    private double quantite;
    @ManyToOne
    @JoinColumn(name = "achat_matiere_premiere_id")
    private AchatMatierePremiere achatMatierePremiere;

    public SortieMatierePremiere() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public AchatMatierePremiere getAchatMatierePremiere() {
        return achatMatierePremiere;
    }

    public void setAchatMatierePremiere(AchatMatierePremiere achatMatierePremiere) {
        this.achatMatierePremiere = achatMatierePremiere;
    }

    public SortieMatierePremiere(Date dateSortie, double quantite, AchatMatierePremiere achatMatierePremiere) {
        this.dateSortie = dateSortie;
        this.quantite = quantite;
        this.achatMatierePremiere = achatMatierePremiere;
    }
}
