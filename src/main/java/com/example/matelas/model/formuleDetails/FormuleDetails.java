package com.example.matelas.model.formuleDetails;

import com.example.matelas.model.formule.Formule;
import com.example.matelas.model.matierepremier.MatierePremier;
import jakarta.persistence.*;

@Entity
public class FormuleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "formule_id")
    private Formule formule;
    @OneToOne
    @JoinColumn(name = "matiere_premier_id")
    private MatierePremier matierePremier;
    private double quantite;

    public FormuleDetails() {
    }

    public FormuleDetails( Formule formule, MatierePremier matierePremier, double quantite) {
        this.formule = formule;
        this.matierePremier = matierePremier;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Formule getFormule() {
        return formule;
    }

    public void setFormule(Formule formule) {
        this.formule = formule;
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
}
